package ink.breakpoint.easypay.transaction.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import ink.breakpoint.easypay.common.cache.util.AmountUtil;
import ink.breakpoint.easypay.common.cache.util.EncryptUtil;
import ink.breakpoint.easypay.common.cache.util.ParseURLPairUtil;
import ink.breakpoint.easypay.transaction.api.PayChannelService;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import ink.breakpoint.easypay.transaction.api.dto.OrderResultDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.vo.OrderConfirmVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class PayController {

    @Reference
    TransactionService transactionService;

    @Reference
    PayChannelService payChannelService;


    @GetMapping("qr/{token}")
    public String entry(@PathVariable String token, HttpServletRequest request) throws Exception {

        //1.解析token数据
        String s = EncryptUtil.decodeUTF8StringBase64(token);
        PayOrderDTO payOrderDTO = JSON.parseObject(s, PayOrderDTO.class);
        log.info("{}", payOrderDTO);

        //2.判断当前请求是来自微信或者支付宝 （使用header中user-Agent的值来判断）
        String header = request.getHeader("User-Agent");
        if (header != null && header.contains("AlipayClient")) {
            //支付宝支付

            return "forward:/pay-page?" + ParseURLPairUtil.parseURLPair(payOrderDTO);
        }

        return "forward:/pay-page?" + ParseURLPairUtil.parseURLPair(payOrderDTO);
    }

    @RequestMapping("pay-page")
    public String payPage() {

        return "pay";
    }


    /**
     * 支付宝下单
     *
     * @param orderConfirmVo
     * @param response
     * @throws Exception
     */
    @ApiOperation("支付宝下单")
    @PostMapping("createAliPayOrder")
    public void createAliPayOrder(OrderConfirmVo orderConfirmVo, HttpServletResponse response) throws Exception {

        PayOrderDTO payOrderDTO = new PayOrderDTO();
        BeanUtils.copyProperties(orderConfirmVo, payOrderDTO);

        //订单金额参数转换问题
        String s = AmountUtil.changeY2F(orderConfirmVo.getTotalAmount());
        payOrderDTO.setTotalAmount(Integer.valueOf(s));

        payOrderDTO.setStoreId(Long.valueOf(orderConfirmVo.getStoreId()));

        String alipayHtml = transactionService.createAliPayOrder(payOrderDTO);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(alipayHtml);
        response.getWriter().flush();
        response.getWriter().close();

    }


    /**
     * 支付宝异步通知
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws AlipayApiException
     */
    @ApiOperation("支付宝异步通知")
    @PostMapping("alipay/notify")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        //获取支付宝请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> map = new HashMap<>();
        parameterMap.keySet().forEach(key -> {
            map.put(key, parameterMap.get(key)[0]);
        });
        log.info("{}", map);
        String outTradeNo = map.get("out_trade_no");


        // 1.验证签名
        String platformChannel = "huimin_c2b", payChannel = "ALIPAY_WAP";
        //  第二种根据订单号查询订单信息
        OrderResultDTO orderResultDTO = transactionService.selectPayOrder(outTradeNo);

        PayChannelParamDTO payChannelParamDTO = payChannelService.queryParamByAppPlatformAndPayChannel(orderResultDTO.getAppId(), platformChannel, payChannel);
        String param = payChannelParamDTO.getParam();
        AliConfigParam aliConfigParam = JSON.parseObject(param, AliConfigParam.class);

        String publicKey = aliConfigParam.getAlipayPublicKey();//支付宝公钥
        boolean b = AlipaySignature.rsaCheckV1(map, publicKey, "utf-8", "RSA2");
        if (!b) {
            response.getWriter().write("error");
        }


        //2.跟新状态
        String trade_status = map.get("trade_status");
        String tradeState = null;

        //交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-交易关闭
        if (trade_status.equals("TRADE_CLOSED")) {//未付款交易超时关闭，或支付完成后全额退款。
            tradeState = "4";
        } else if (trade_status.equals("TRADE_SUCCESS")) {//交易支付成功
            tradeState = "2";
        } else if (trade_status.equals("TRADE_FINISHED")) {//交易结束，不可退款
            tradeState = "3";
        }


        OrderResultDTO orderResultDTO2 = new OrderResultDTO();
        orderResultDTO2.setTradeState(tradeState);
        orderResultDTO2.setOutTradeNo(outTradeNo);
        orderResultDTO2.setPaySuccessTime(LocalDateTime.now());

        transactionService.updatePayOrderByTradeNo(orderResultDTO2);

        //3.如果成功回写success
        response.getWriter().write("success");
        response.getWriter().flush();
        response.getWriter().close();
    }


    @ApiOperation("支付宝同步通知地址")
    @RequestMapping("alipay/success/{tradeNo}")
    public String success(@PathVariable String tradeNo, Model model) {
        OrderResultDTO orderResultDTO = transactionService.selectPayOrder(tradeNo);
        String msg = "支付失败！";
        String msg_notice = "请用支付宝再次扫描支付";

        if (orderResultDTO != null) {
            String tradeState = orderResultDTO.getTradeState();
            if (tradeState.equals("2") || tradeState.equals("3")) {
                msg = "支付成功";
            }
        }

        model.addAttribute("msg", msg);
        model.addAttribute("msg_notice", msg_notice);


        return "pay_error";
    }

}