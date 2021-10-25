package ink.breakpoint.easypay.transaction.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.util.AmountUtil;
import ink.breakpoint.easypay.common.cache.util.EncryptUtil;
import ink.breakpoint.easypay.common.cache.util.PaymentUtil;
import ink.breakpoint.easypay.merchant.service.api.AppService;
import ink.breakpoint.easypay.merchant.service.api.MerchantService;
import ink.breakpoint.easypay.paymentagent.api.PayChannelAgentService;
import ink.breakpoint.easypay.paymentagent.api.conf.AliConfigParam;
import ink.breakpoint.easypay.paymentagent.api.dto.AlipayBean;
import ink.breakpoint.easypay.paymentagent.api.dto.PaymentResponseDTO;
import ink.breakpoint.easypay.transaction.api.PayChannelService;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.api.dto.QrcodeDto;
import ink.breakpoint.easypay.transaction.convert.PayOrderConvert;
import ink.breakpoint.easypay.transaction.entity.PayOrder;
import ink.breakpoint.easypay.transaction.mapper.PayOrderMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Reference
    MerchantService merchantService;
    @Reference
    AppService appService;

    @Reference
    PayChannelService payChannelService;

    @Reference
    PayChannelAgentService payChannelAgentService;

    @Autowired
    PayOrderMapper payOrderMapper;
    /**
     * 支付入口url
     */
    @Value("${easypay.payurl}")
    private String payurl;

    /**
     * 生成门店二维码
     *
     * @param qrCodeDto 传入merchantId、appId、storeId、channel、subject、body
     * @return -
     * @throws BusinessException -
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
//        return "http://172.16.42.122:56010/transaction/alipaytest";
    }

    @Override
    public PaymentResponseDTO submitOrderByAliPay(PayOrderDTO payOrderDTO) throws BusinessException {
        //保存订单，设置平台支付渠道
        payOrderDTO.setPayChannel("ALIPAY_WAP");
        //保存订单到聚合支付平台数据库
        PayOrderDTO save = save(payOrderDTO);
        //调用代理支付服务，向支付宝发送订单请求
        return alipayH5(save.getTradeNo());
    }


    @Override
    public void updateOrderTradeNoAndTradeState(String tradeNo, String payChannelTradeNo, String state) {
        final LambdaUpdateWrapper<PayOrder> lambda = new UpdateWrapper<PayOrder>().lambda();
        lambda.eq(PayOrder::getTradeNo, tradeNo).set(PayOrder::getPayChannelTradeNo, payChannelTradeNo)
                .set(PayOrder::getTradeState, state);
        if ("2".equals(state)) {
            lambda.set(PayOrder::getPaySuccessTime, LocalDateTime.now());
        }
        payOrderMapper.update(null, lambda);
    }

    /**
     * \* 保存订单到惠民平台
     * \* @param payOrderDTO
     * \* @return
     */
    //保存订单（公用）
    private PayOrderDTO save(PayOrderDTO payOrderDTO) throws BusinessException {

        PayOrder payOrder = PayOrderConvert.INSTANCE.dto2entity(payOrderDTO);
        //订单号
        payOrder.setTradeNo(PaymentUtil.genUniquePayOrderNo());//采用雪花片算法
        //订单创建时间
        payOrder.setCreateTime(LocalDateTime.now());
        //过期时间是30分钟后
        payOrder.setExpireTime(LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
        //支付币种  人民币
        payOrder.setCurrency("CNY");
        payOrder.setTradeState("0");//订单状态，0：订单生成
        payOrderMapper.insert(payOrder);//插入订单
        return PayOrderConvert.INSTANCE.entity2dto(payOrder);
    }

    //调用支付宝下单接口
    private PaymentResponseDTO alipayH5(String tradeNo) {
        //构建支付实体
        AlipayBean alipayBean = new AlipayBean();

        //根据订单号查询订单详情
        PayOrderDTO payOrderDTO = queryPayOrder(tradeNo);
        alipayBean.setOut_trade_no(tradeNo);
        alipayBean.setSubject(payOrderDTO.getSubject());
        String totalAmount = null;//支付宝那边入参是元
        try {
            //将分转成元
            totalAmount = AmountUtil.changeF2Y(payOrderDTO.getTotalAmount().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_400001);
        }
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(payOrderDTO.getBody());
        alipayBean.setStoreId(payOrderDTO.getStoreId());
        alipayBean.setExpireTime("30m");
//        alipayBean.setProduct_code("QUICK_WAP_PAY");

        //根据应用、服务类型、支付渠道查询支付渠道参数
        PayChannelParamDTO payChannelParamDTO = payChannelService.queryParamByAppPlatformAndPayChannel(payOrderDTO.getAppId(),
                payOrderDTO.getChannel(), "ALIPAY_WAP");
        if (payChannelParamDTO == null) {
            throw new BusinessException(CommonErrorCode.E_400001);
        }
        //支付宝渠道参数
        AliConfigParam aliConfigParam = JSON.parseObject(payChannelParamDTO.getParam(),
                AliConfigParam.class);
        //字符编码
        aliConfigParam.setCharest("utf-8");
        return payChannelAgentService
                .createPayOderByAliWAP(aliConfigParam, alipayBean);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param tradeNo -
     * @return -
     */
    private PayOrderDTO queryPayOrder(String tradeNo) {
        PayOrder payOrder = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getTradeNo, tradeNo));
        return PayOrderConvert.INSTANCE.entity2dto(payOrder);
    }

    /**
     * 校验门店和app合法性
     *
     * @param merchantId -
     * @param appId      -
     * @param storeId    -
     */
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
