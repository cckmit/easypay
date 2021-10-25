package ink.breakpoint.easypay.paymentagent.api.common;

/**
 * 公用状态码
 */
public final class AliCodeConstants {
    /**
     * 支付宝拆线呢返回状态码
     */
    public static final String SUCCESS_CODE = "10000"; //支付成功或接口调用成功
    public static final String PAYING_CODE = "10003"; //用户支付中
    public static final String FAILED_CODE = "40004"; //失败
    public static final String ERROR_CODE = "20000"; //系统异常
    /**
     * 支付宝交易状态
     */
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; //交易创建，等待买家付款
    public static final String TRADE_CLOSED = "TRADE_CLOSED"; //未付款，交易超时关闭，或支付完成全额退款
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS"; //交易支付成功
    public static final String TRADE_FINISHED = "TRADE_FINISHED"; //交易结束，不可退款

    private AliCodeConstants() {
    }
}
