package ink.breakpoint.easypay.transaction.service;

import com.alibaba.fastjson.JSON;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.util.EncryptUtil;
import ink.breakpoint.easypay.merchant.service.api.AppService;
import ink.breakpoint.easypay.merchant.service.api.MerchantService;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.api.dto.QrcodeDto;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Reference
    MerchantService merchantService;
    @Reference
    AppService appService;
    /**
     * 支付入口url
     */
    @Value("${easypay.payurl}")
    private String payurl;

    /**
     * 生成门店二维码
     *
     * @param qrCodeDto 传入merchantId、appId、storeId、channel、subject、body
     * @return
     * @throws BusinessException
     */
    @Override
    public String createStoreQRCode(QrcodeDto qrCodeDto) throws BusinessException {
        // 校验应用和门店
        verifyAppAndStore(qrCodeDto.getMerchantId(), qrCodeDto.getAppId(), qrCodeDto.getStoreId());

        //1. 生成支付信息
        PayOrderDTO payOrderDTO = new PayOrderDTO();
        payOrderDTO.setMerchantId(qrCodeDto.getMerchantId());
        payOrderDTO.setAppId(qrCodeDto.getAppId());
        payOrderDTO.setStoreId(qrCodeDto.getStoreId());
        payOrderDTO.setSubject(qrCodeDto.getSubject());//显示订单标题
        payOrderDTO.setChannel("huimin_c2b");//服务类型
        payOrderDTO.setBody(qrCodeDto.getBody());//订单内容
        String jsonString = JSON.toJSONString(payOrderDTO);

        // 将支付信息保存到票据中
        String ticket = EncryptUtil.encodeUTF8StringBase64(jsonString);
        //支付入口
        return payurl + ticket;
    }

    private void verifyAppAndStore(Long merchantId, String appId, Long storeId) {
        //判断应用是否属于商户
        Boolean a = appService.queryAppInMerchant(appId, merchantId);
        if (!a) {
            throw new BusinessException(CommonErrorCode.E_200005);
        }
        //判断当前们段是否属于当前商户
        Boolean b = merchantService.queryStoreInMerchant(storeId, merchantId);
        if (!b) {
            throw new BusinessException(CommonErrorCode.E_200006);
        }
    }
}
