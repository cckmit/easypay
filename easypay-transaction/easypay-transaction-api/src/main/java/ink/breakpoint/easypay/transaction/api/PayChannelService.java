package ink.breakpoint.easypay.transaction.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.api.dto.PlatformChannelDTO;

import java.util.List;

public interface PayChannelService {
    /**
     * 查询服务类型列表
     * @return 服务类型列表
     * @throws BusinessException
     */
    List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException;

    /**
     * 给应用绑定服务类型
     * @param appId 应用id
     * @param platformChannelCode 服务类型代码
     */
    void bindPlatformChannelForApp(String appId,String platformChannelCode) throws BusinessException;

    /**
     * 查询服务类型绑定状态
     * @param appId 应用id
     * @param platformChannelCode 服务类型代码
     * @return 状态编码
     * @throws BusinessException
     */
    int queryAppBindPlatformChannel(String appId,String platformChannelCode) throws BusinessException;


    /**
     \* 根据平台服务类型获取支付渠道列表
     \* @param platformChannelCode
     \* @return
     */
    List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws
            BusinessException;


    /**
     /* 保存支付渠道参数
     /* @param payChannelParam 商户原始支付渠道参数
     */
    void savePayChannelParam(PayChannelParamDTO payChannelParam) throws BusinessException;


    /**
     * 根据应用和服务类型查询支付渠道参数列表     结果可能是多个(支付宝param 微信param)
     * @param appId 应用id
     * @param platformChannel 服务类型code
     * @return
     */
    List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appId, String
            platformChannel)
            throws BusinessException;

    /**
     \* 获取指定应用指定服务类型下所包含的某个原始支付参数
     \* @param appId
     \* @param platformChannel
     \* @param payChannel
     \* @return
     \* @throws BusinessException
     */
    PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String platformChannel, String payChannel) throws BusinessException;



}
