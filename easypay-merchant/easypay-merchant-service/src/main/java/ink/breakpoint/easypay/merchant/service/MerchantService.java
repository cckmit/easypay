package ink.breakpoint.easypay.merchant.service;

import ink.breakpoint.easypay.merchant.entity.Merchant;
import ink.breakpoint.easypay.merchant.mapper.MerchantMapper;
import ink.breakpoint.easypay.merchant.service.api.IMerchantService;
import ink.breakpoint.easypay.merchant.service.dto.MerchantDTO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MerchantService implements IMerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @Override
    public MerchantDTO queryMerchantById(Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant,merchantDTO);
        return merchantDTO;
    }
}
