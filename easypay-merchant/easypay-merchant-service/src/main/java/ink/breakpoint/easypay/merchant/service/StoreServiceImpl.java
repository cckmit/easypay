package ink.breakpoint.easypay.merchant.service;

import ink.breakpoint.easypay.merchant.entity.Store;
import ink.breakpoint.easypay.merchant.mapper.StoreMapper;
import ink.breakpoint.easypay.merchant.service.api.StoreService;
import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreMapper storeMapper;


    @Override
    public StoreDTO slectById(Long storeId) {
        Store store = storeMapper.selectById(storeId);
        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(store, storeDTO);

        return storeDTO;
    }
}
