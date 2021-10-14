package ink.breakpoint.easypay.merchant.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.util.RandomUuidUtil;
import ink.breakpoint.easypay.merchant.convert.AppConvert;
import ink.breakpoint.easypay.merchant.entity.App;
import ink.breakpoint.easypay.merchant.entity.Merchant;
import ink.breakpoint.easypay.merchant.mapper.AppMapper;
import ink.breakpoint.easypay.merchant.mapper.MerchantMapper;
import ink.breakpoint.easypay.merchant.service.api.AppService;
import ink.breakpoint.easypay.merchant.service.api.dto.AppDTO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service // dubbo注解
public class AppServiceImpl implements AppService {

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    AppMapper appMapper;

    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) {
        // 非空校验
        if (merchantId == null || appDTO == null || appDTO.getAppName() == null ) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        // 校验merchantId的合法性
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200227);
        }
        // 看商户是否通过审核(获取状态)
        if (!"2".equals(merchant.getAuditStatus())){
            throw new BusinessException(CommonErrorCode.E_200236);
        }
        // 应用名称不能重复
        if (isExists(appDTO.getAppName())) {
            throw new BusinessException(CommonErrorCode.E_200237);
        }
        // dto转entity
        App app = AppConvert.INSTANCE.dto2entity(appDTO);
        // 设置关键信息
        app.setAppId(RandomUuidUtil.getUUID());
        app.setMerchantId(merchantId);
        // 保存应用
        appMapper.insert(app);
        return AppConvert.INSTANCE.entity2dto(app);
    }

    @Override
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
        List<App> appList = appMapper.selectList(new LambdaQueryWrapper<App>().eq(App::getMerchantId, merchantId));
        return AppConvert.INSTANCE.listEntity2dto(appList);
    }

    @Override
    public AppDTO getAppById(String appId) throws BusinessException {
        App app = appMapper.selectOne(new LambdaQueryWrapper<App>().eq(App::getAppId, appId));
        return AppConvert.INSTANCE.entity2dto(app);
    }

    /**
     * 判断应用名称是否已存在
     *
     * @param appName 应用名称
     * @return 是否存在
     */
    private Boolean isExists(String appName) {
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName, appName));
        return count > 0;
    }


    /**
     * 编辑更新app
     *
     * @param appDTO appDTO
     * @return -更新后的app
     * @throws BusinessException -
     */
    @Override
    public AppDTO updateApp(AppDTO appDTO) throws BusinessException {
        App app = AppConvert.INSTANCE.dto2entity(appDTO);
        int update = appMapper.update(app, new LambdaQueryWrapper<App>().eq(App::getMerchantId, appDTO.getMerchantId()).eq(App::getAppId, appDTO.getAppId()));
        if (1 != update) {
            throw new BusinessException(CommonErrorCode.E_110006);
        }

        return this.getAppById(appDTO.getAppId());
    }

    /**
     * 查询应用是否属于某个应用
     *
     * @param appId      appid
     * @param merchantId 商户id
     * @return -
     */
    @Override
    public Boolean queryAppInMerchant(String appId, Long merchantId) {
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppId, appId).eq(App::getMerchantId, merchantId));
        return count > 0;
    }
}
