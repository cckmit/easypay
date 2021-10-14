package ink.breakpoint.easypay.transaction.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.transaction.api.dto.QrcodeDto;

public interface TransactionService {


    /**
     * 生成门店二维码
     *
     * @param qrcodeDto 传入merchantId、appId、storeId、channel、subject、body
     * @return 支付入口URL，将二维码的参数组成的接json并用base64编码
     * @throws BusinessException -
     */
    String createStoreQRCode(QrcodeDto qrcodeDto) throws BusinessException;
}
