package ink.breakpoint.easypay.merchant.controller;

import ink.breakpoint.easypay.merchant.service.api.IMerchantService;
import ink.breakpoint.easypay.merchant.service.dto.MerchantDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {

    @Reference
    IMerchantService merchantService;

    /**
     * 根据ID查询商户信息
     */
    @GetMapping("/merchant/{id}")
    public MerchantDTO findMerchantById(@PathVariable("id")Long id){
        return merchantService.queryMerchantById(id);
    }
}
