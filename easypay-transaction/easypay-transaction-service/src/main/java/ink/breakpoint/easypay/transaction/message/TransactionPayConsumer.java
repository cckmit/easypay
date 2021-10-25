package ink.breakpoint.easypay.transaction.message;

import com.alibaba.fastjson.JSON;
import ink.breakpoint.easypay.paymentagent.api.dto.PaymentResponseDTO;
import ink.breakpoint.easypay.paymentagent.api.dto.TradeStatus;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@RocketMQMessageListener(topic = "result", consumerGroup = "C_AliPayProducer")
@Component
public class TransactionPayConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private TransactionService transactionService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        PaymentResponseDTO res = JSON.parseObject(body, PaymentResponseDTO.class);
        final TradeStatus tradeState = res.getTradeState();
        String payChannelTradeNo = res.getTradeNo();
        String tradeNo = res.getOutTradeNo();
        switch (tradeState) {
            case SUCCESS:
                //支付成功时，修改订单状态为支付成功
                transactionService.updateOrderTradeNoAndTradeState(tradeNo, payChannelTradeNo, "2");
                return;
            case REVOKED:
                //支付关闭时，修改订单状态为关闭
                transactionService.updateOrderTradeNoAndTradeState(tradeNo, payChannelTradeNo, "4");
                return;
            case FAILED:
                //支付失败时，修改订单状态为失败
                transactionService.updateOrderTradeNoAndTradeState(tradeNo, payChannelTradeNo, "5");
                return;
            default:
                throw new RuntimeException(String.format("无法解析支付结果:%s", body));
        }
    }

}
