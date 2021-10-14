package ink.breakpoint.easypay.merchant.service.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.merchant.service.api.dto.AppDTO;

import java.util.List;

public interface AppService {
    /**
     * 创建应用
     * @param merchantId 商户id
     * @param appDTO 应用信息
     * @return 应用信息
     */
    AppDTO createApp(Long merchantId,AppDTO appDTO) throws BusinessException;

    /**
     * 根据商户id查询应用列表
     * @param merchantId 商户id
     * @return 应用列表
     * @throws BusinessException 自定义异常
     */
    List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException;

    /**
     * 根据应用id查询应用详情
     *
     * @param appId 应用id
     * @return 应用信息
     * @throws BusinessException 自定义异常
     */
    AppDTO getAppById(String appId) throws BusinessException;


    /**
     * 根据merchantId更新app信息
     *
     * @param appDTO appDTO
     * @return 更新后的appDTO
     * @throws BusinessException -
     */
    AppDTO updateApp(AppDTO appDTO) throws BusinessException;

    /**
     * 查询应用是否属于某个商户
     *
     * @param appId      appid
     * @param merchantId 商户id
     * @return -
     */
    Boolean queryAppInMerchant(String appId, Long merchantId);
}
