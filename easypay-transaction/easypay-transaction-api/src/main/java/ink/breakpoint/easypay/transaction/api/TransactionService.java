package ink.breakpoint.easypay.transaction.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.paymentagent.api.dto.PaymentResponseDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
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


    PaymentResponseDTO submitOrderByAliPay(PayOrderDTO payOrderDTO) throws BusinessException;


    /**
     * \* 更新订单支付状态
     * \* @param tradeNo  惠民平台订单号
     * \* @param payChannelTradeNo 支付宝或微信的交易流水号
     * \* @param state  订单状态 交易状态支付状态,0‐订单生成,1‐支付中(目前未使用),2‐支付成功,4‐关闭 5‐‐失败
     */
    void updateOrderTradeNoAndTradeState(String tradeNo, String payChannelTradeNo, String state);

}
