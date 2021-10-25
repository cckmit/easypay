package ink.breakpoint.easypay.paymentagent.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.paymentagent.api.conf.AliConfigParam;
import ink.breakpoint.easypay.paymentagent.api.dto.AlipayBean;
import ink.breakpoint.easypay.paymentagent.api.dto.PaymentResponseDTO;

public interface PayChannelAgentService {
    /**
     * 确认支付调用alipay WAP下单支付接口服务
     *
     * @param aliConfigParam 支付渠道参数 包含应用中配置的第三方支付渠道的支付参数
     * @param alipayBean     请求支付参数 包含订单信息 商品信息
     * @return -
     * @throws BusinessException -
     */
    PaymentResponseDTO createPayOderByAliWAP(AliConfigParam aliConfigParam, AlipayBean alipayBean) throws BusinessException;

    PaymentResponseDTO queryPayOrderByAliPay(AliConfigParam aliconfigParam, String outTradeNo);
}
