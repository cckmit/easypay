package ink.breakpoint.easypay.merchant.service.api;

import ink.breakpoint.easypay.merchant.service.dto.MerchantDTO;

public interface IMerchantService{
    /**
     * 根据ID查询商户信息
     * @param merchantId
     * @return
     */
    MerchantDTO queryMerchantById(Long   merchantId);


}
