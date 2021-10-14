package ink.breakpoint.easypay.merchant.service.api;

import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;

public interface StoreService {

    /**
     * 门店二维码
     *
     * @param storeId
     * @return
     */
    StoreDTO slectById(Long storeId);
}