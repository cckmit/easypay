package ink.breakpoint.easypay.merchant.controller;

import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.common.cache.util.QRCodeUtil;
import ink.breakpoint.easypay.merchant.common.util.SecurityUtil;
import ink.breakpoint.easypay.merchant.service.api.MerchantService;
import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import ink.breakpoint.easypay.transaction.api.dto.QrcodeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * 门店管理
 */
@Api(value = "商户平台‐门店管理", tags = "商户平台‐门店管理", description = "商户平台‐门店的增删改查")
@RestController
public class StoreController {

    @Reference
    MerchantService merchantService;

    @Reference
    TransactionService transactionService;

    //门店二维码订单标题
    @Value("${easypay.c2b.subject}")
    private String subject;

    //门店二维码订单内容
    @Value("${easypay.c2b.body}")
    private String body;

    @ApiOperation("分页条件查询商户下门店")
    @PostMapping("/my/stores/merchants/page")
    public PageVO<StoreDTO> queryStoreByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {


        //查询id
        Long merchantId = SecurityUtil.getMerchantId();

        //查询条件
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setMerchantId(merchantId);//商户id

        //调用service查询列表
        PageVO<StoreDTO> pageVO = merchantService.queryStoreByPage(storeDTO, pageNo, pageSize);

        return pageVO;
    }


    @ApiOperation("生成商户应用门店二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "商户应用ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "storeId", value = "商户门店id", required = true, dataType = "String", paramType = "path")
    })
    //http://localhost:8080/merchant/my/apps/87a2702d0df64669aa73924dd13bd969/stores/1449955397829902300/app-store-qrcode?tenantId=12&body=&totalAmount=
    @GetMapping("/my/apps/{appId}/stores/{storeId}/app-store-qrcode")
    public String createCScanBStoreQRCode(@PathVariable String appId, @PathVariable Long storeId) {
        //获取商户id
        Long merchantId = SecurityUtil.getMerchantId();
        //查询商户信息
        MerchantDTO merchantDTO = merchantService.queryMerchantById(merchantId);

        //生成二维码链接
        QrcodeDto qrcodeDto = new QrcodeDto();
        qrcodeDto.setMerchantId(merchantId);
        qrcodeDto.setAppId(appId);
        qrcodeDto.setStoreId(storeId);
        //格式化subject
        String subjectFormat = String.format(subject, merchantDTO.getMerchantName());
        qrcodeDto.setSubject(subjectFormat);
        //格式化body
        String bodyFormat = String.format(body, merchantDTO.getMerchantName());
        qrcodeDto.setBody(bodyFormat);
        //获取二维码URL
        String storeQRCodeURL = transactionService.createStoreQRCode(qrcodeDto);
        return QRCodeUtil.createQRCode(storeQRCodeURL, 200, 200);
    }


}