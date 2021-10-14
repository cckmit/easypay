package ink.breakpoint.easypay.transaction.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderConfirmVo implements Serializable {

    private String appId; //应用id
    private String tradeNo;//交易单号
    private String openId;//微信openid
    private String storeId;//门店id
    private String channel; //服务类型
    private String body;//订单描述
    private String subject;//订单标题
    private String totalAmount;//金额
}