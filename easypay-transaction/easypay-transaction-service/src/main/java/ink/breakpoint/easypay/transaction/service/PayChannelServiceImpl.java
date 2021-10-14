package ink.breakpoint.easypay.transaction.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ink.breakpoint.easypay.common.cache.Cache;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.util.RedisUtil;
import ink.breakpoint.easypay.transaction.api.PayChannelService;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.api.dto.PlatformChannelDTO;
import ink.breakpoint.easypay.transaction.convert.PayChannelParamConvert;
import ink.breakpoint.easypay.transaction.convert.PlatformChannelConvert;
import ink.breakpoint.easypay.transaction.entity.AppPlatformChannel;
import ink.breakpoint.easypay.transaction.entity.PayChannelParam;
import ink.breakpoint.easypay.transaction.entity.PlatformChannel;
import ink.breakpoint.easypay.transaction.mapper.AppPlatformChannelMapper;
import ink.breakpoint.easypay.transaction.mapper.PayChannelParamMapper;
import ink.breakpoint.easypay.transaction.mapper.PlatformChannelMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service // dubbo注解
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    PlatformChannelMapper platformChannelMapper;

    @Autowired
    private PayChannelParamMapper payChannelParamMapper;

    @Autowired
    AppPlatformChannelMapper appPlatformChannelMapper;

    @Autowired
    private Cache cache;


    /**
     * 查询平台服务列表 platformChannel
     * @return platformChannelList
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        List<PlatformChannel> channelList = platformChannelMapper.selectList(null);
        return PlatformChannelConvert.INSTANCE.listentity2listdto(channelList);
    }


    /**
     * 给app绑定服务
     * @param appId 应用id
     * @param platformChannelCode 服务类型代码
     * @throws BusinessException 自定义异常
     */
    @Override
    public void bindPlatformChannelForApp(String appId, String platformChannelCode) throws BusinessException {
        // 根据APPId 和 platformChannelCode去数据库查询是否有记录
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>()
                .eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        // 没有的话再绑定
        if (appPlatformChannel == null) {
            appPlatformChannel = new AppPlatformChannel();
            appPlatformChannel.setAppId(appId);
            appPlatformChannel.setPlatformChannel(platformChannelCode);
            appPlatformChannelMapper.insert(appPlatformChannel);
        }
    }

    /**
     * 查询app是否绑定某个支付服务
     * @param appId 应用id
     * @param platformChannelCode 服务类型代码
     * @return 返回绑定关系【0：未绑定】、【1：已绑定】
     * @throws BusinessException
     */
    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannelCode) throws BusinessException {
        // 根据APPId 和 platformChannelCode去数据库查询是否有记录
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>()
                .eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if (appPlatformChannel == null) {
            return 0;
        }
        //已存在绑定关系返回1
        return 1;
    }

    /**
     * 查询支付服务支持的第三方支付渠道
     * @param platformChannelCode 平台支付服务类型代码
     * @return 返回平台支付服务支持的第三方支付渠道列表
     */
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) {

        return platformChannelMapper.selectPayChannelByPlatformChannel(platformChannelCode);
    }


    /**
     \* 保存支付渠道参数
     并更新redis缓存
     \* @param payChannelParamDTO 支付渠道参数
     \* @throws BusinessException
     */
    @Override
    public void savePayChannelParam(PayChannelParamDTO payChannelParamDTO) throws
            BusinessException {
        //非空校验
        if(payChannelParamDTO == null || StringUtils.isBlank(payChannelParamDTO.getAppId())
                ||
                StringUtils.isBlank(payChannelParamDTO.getPlatformChannelCode())
                ||
                StringUtils.isBlank(payChannelParamDTO.getPayChannel())){
            throw new BusinessException(CommonErrorCode.E_200017);
        }
        //根据appid和服务类型查询应用与服务类型绑定id
        Long appPlatformChannelId = selectIdByAppPlatformChannel(payChannelParamDTO.getAppId(),
                payChannelParamDTO.getPlatformChannelCode());
        if(appPlatformChannelId == null){
            //应用未绑定该服务类型不可进行支付渠道参数配置
            throw new BusinessException(CommonErrorCode.E_200212);
        }
        //根据应用与服务类型绑定id和支付渠道查询参数信息
        PayChannelParam payChannelParam = payChannelParamMapper.selectOne(new
                LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId,
                        appPlatformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannelParamDTO.getPayChannel()));
        //更新已有配置
        if (payChannelParam!=null){
            payChannelParam.setChannelName(payChannelParamDTO.getChannelName());
            payChannelParam.setParam(payChannelParamDTO.getParam());
            payChannelParamMapper.updateById(payChannelParam);
        }else{
            //添加新配置
            PayChannelParam entity =   PayChannelParamConvert.INSTANCE.dto2entity(payChannelParamDTO);
            entity.setId(null);
            //应用与服务类型绑定id
            entity.setAppPlatformChannelId(appPlatformChannelId);
            payChannelParamMapper.insert(entity);
        }
        //更新参数列表缓存
        updateCache(payChannelParamDTO.getAppId(),payChannelParamDTO.getPlatformChannelCode());
    }

    /**
     * 更新支付参数列表缓存
     * @param appId appid
     * @param platformChannel platformchannel
     */
    private void updateCache(String appId, String platformChannel) {
        //处理redis缓存
        //1.key的构建 如：HM_PAY_PARAM:b910da455bc84514b324656e1088320b:huimin_c2b
        String redisKey = RedisUtil.keyBuilder(appId, platformChannel);
        //2.查询redis,检查key是否存在
        if (cache.exists(redisKey)) {//存在，则清除
            //删除原有缓存
            cache.del(redisKey);
        }
        //3.从数据库查询应用的服务类型对应的实际支付参数，并重新存入缓存
        List<PayChannelParamDTO> paramDTOS =
                queryPayChannelParamByAppAndPlatform(appId,platformChannel);
        if (paramDTOS != null) {
            //存入缓存
            cache.set(redisKey, JSON.toJSON(paramDTOS).toString());
        }
    }

    /**
     * 根据应用id 、服务类型 code 查询应用与服务类型的绑定id
     * @param appId
     * @param platformChannelCode
     * @return
     */
    private Long selectIdByAppPlatformChannel(String appId,String platformChannelCode){
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if(appPlatformChannel!=null){
            return appPlatformChannel.getId();//应用与服务类型的绑定id
        }
        return null;
    }

    /**
     * 根据应用和服务类型查询支付渠道参数列表
     *
     * 实现redis缓存
     *
     * @param appId           应用id
     * @param platformChannel 服务类型code
     * @return 支付参数列表
     */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appId, String platformChannel) {
        //先从redis查询，如果有则返回
        //从缓存查询
        //1.key的构建 如：HM_PAY_PARAM:b910da455bc84514b324656e1088320b:huimin_c2b
        String redisKey = RedisUtil.keyBuilder(appId, platformChannel);
        //是否有缓存
        if(cache.exists(redisKey)){
            //从redis获取支付渠道参数列表（json串）
            String PayChannelParamDTO_String = cache.get(redisKey);
            //将json串转成 List<PayChannelParamDTO>返回
            return JSON.parseArray(PayChannelParamDTO_String, PayChannelParamDTO.class);
        }

        //缓存中没有 则从数据库查询  根据应用和服务类型找到它们绑定id
        Long appPlatformChannelId = selectIdByAppPlatformChannel(appId, platformChannel);
        if(appPlatformChannelId == null){
            return null;
        }
        //应用和服务类型绑定id查询支付渠道参数记录
        List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId));
        List<PayChannelParamDTO> payChannelParamDTOList = PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
        //保存到redis
        String key = RedisUtil.keyBuilder(appId, platformChannel);
        String value = JSON.toJSONString(payChannelParamDTOList);
        cache.set(key,value);
        //返回参数列表
        return payChannelParamDTOList;
    }


    /**
     *查询app对应某个平台服务中某个第三方支付渠道的支付参数
     *
     * @param appId appid
     * @param platformChannel 平台服务类型code
     * @param payChannel 第三方支付渠道
     * @return 对应的第三方支付渠道支付参数
     * @throws BusinessException 自定义异常
     */
    @Override
    public PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String platformChannel, String payChannel) throws BusinessException {
        List<PayChannelParamDTO> payChannelParamDTOS = queryPayChannelParamByAppAndPlatform(appId, platformChannel);
        if (payChannelParamDTOS != null) {
            for (PayChannelParamDTO payChannelParam : payChannelParamDTOS) {
                if (payChannelParam.getPayChannel().equals(payChannel)) {
                    return payChannelParam;
                }
            }
        }
        return null;
    }

}
