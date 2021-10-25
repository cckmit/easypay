package ink.breakpoint.easypay.transaction.controller;


import com.alibaba.fastjson.JSON;
import ink.breakpoint.easypay.common.cache.util.AmountUtil;
import ink.breakpoint.easypay.common.cache.util.EncryptUtil;
import ink.breakpoint.easypay.common.cache.util.IPUtil;
import ink.breakpoint.easypay.common.cache.util.ParseURLPairUtil;
import ink.breakpoint.easypay.merchant.service.api.AppService;
import ink.breakpoint.easypay.merchant.service.api.dto.AppDTO;
import ink.breakpoint.easypay.paymentagent.api.dto.PaymentResponseDTO;
import ink.breakpoint.easypay.transaction.api.TransactionService;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.convert.PayOrderConvert;
import ink.breakpoint.easypay.transaction.vo.OrderConfirmVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class PayController {

    @Reference
    AppService appService;

    @Reference
    TransactionService transactionService;


    /**
     * 平台支付入口 ---- 接受处理门店二维码url
     * 解析二维码中编码的信息，并还原，传递至订单确认页面
     *
     * @return -
     */
    @RequestMapping("/pay-entry/{ticket}")
    public String payEntry(@PathVariable String ticket, HttpServletRequest request) throws Exception {
        //将base64编码的ticket解码还原成json字符串
        String ticketStr = EncryptUtil.decodeUTF8StringBase64(ticket);
        //将解码还原的json字符串解析为payOderDTO对象
        PayOrderDTO payOrderDTO = JSON.parseObject(ticketStr, PayOrderDTO.class);
        //将订单对象中的数据转化成url中拼接的键值对形式
        String urlParam = ParseURLPairUtil.parseURLPair(payOrderDTO);

        //判断扫描二维码的设备，转发只对应的第三方 支付渠道

        String userAgent = request.getHeader("User-Agent");
        BrowserType browserType = BrowserType.valueOfUserAgent(userAgent);
        switch (browserType) {
            case ALIPAY:
                return "forward:/pay-page?" + urlParam;
            case WECHAT:
                return "forward:/pay-page?" + urlParam;
            case PC_BROWSER:
                return "forward:/pay-error";
            default:
        }
        return "forward:/pay-error";
    }

    /**
     * 支付宝下单付款接口
     *
     * @param orderConfirmVo --订单确认信息
     * @param request        http请求对象
     * @param response       http响应对象
     */
    @ApiOperation("支付宝门店下单付款")
    @PostMapping("/createAliPayOrder")
    public void createAlipayOrderForStore(OrderConfirmVo orderConfirmVo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 支付宝的下单接口,前端订单确认页面，点击确认支付，请求进来
        PayOrderDTO payOrderDTO = PayOrderConvert.INSTANCE.vo2dto(orderConfirmVo);
        //应用id
        String appId = payOrderDTO.getAppId();
        //获取下单的应用信息
        AppDTO app = appService.getAppById(appId);
        payOrderDTO.setMerchantId(app.getMerchantId());//商户id
        //将前端输入的 元转成分
        payOrderDTO.setTotalAmount(Integer.parseInt(AmountUtil.changeY2F(orderConfirmVo.getTotalAmount())));
        //客户端ip
        payOrderDTO.setClientIp(IPUtil.getIpAddr(request));
        //保存订单，调用支付渠道代理服务的支付宝下单
        PaymentResponseDTO<String> paymentResponseDTO = transactionService.submitOrderByAliPay(payOrderDTO);
        //支付宝下单接口响应
        String content = paymentResponseDTO.getContent();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(content);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

}