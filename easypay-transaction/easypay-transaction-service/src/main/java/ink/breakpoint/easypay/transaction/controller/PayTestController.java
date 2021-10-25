package ink.breakpoint.easypay.transaction.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PayTestController {

    //APPID
    String APP_ID = "2021000118635333";
    // 应用私钥
    String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCW49k8p3WSAGEjAzwmedrrr8c1P6KM69JomxFHzy2l" +
            "zzji4gp5GuIEV8vYYDdwG24ofd1UdjmXtfNkQ70w8wywLvIC/DZ7VlBU6MO83jAncfbGBEePBuYe1/XWWLdpiVp9DbsAz5jdOlbiPC1d" +
            "rjxN/qCzgfuFlUzEZvYwwrpy1Nq6VMxVphuxzXSpDzCiNIJ+uNJasTxI3T3D9YaQ/eJOmTvmVEgizi6Tj/CK00W2FPUJkC4JizxdASSEM7" +
            "Ql6HhYSHEnNfxX9dLiHtuILQEtjSSdmIFV7HSa/2Apw3k3NAoJHaHhZSD2zWhKu/gEpmk4Ml6MAIQ2/wmS9z9szZnlAgMBAAECggEARlBv" +
            "T0CKHTtoCLTdNuDaD7KeFkZNLXZHWs/IQbpd/0KNs8y6LK1GXde7+lz7+JYPw3bDyTQ+ql/zx+l5JHJem0uFI7b8fIVAchD2BfFUZ01IPga" +
            "IgjIo0n645OX6sod7T4MyfbI//ffNvSdlzJ4BJCdgI8aSuzFT0MJaP2a/E/s8n9J6/pPgFcax72WkxT3mIiUJTIasFDcAo3+tKycEivENlO" +
            "JplbeT1Kq+9PqDWpICAQuVuaSp5Rqqj8OMhd3P7fOHprPp4/wXOVz61Dx/QDfp/nis6nY3cckN4kAIs3c6ATTSjc6A+UwPbV0YYrwjaBHA" +
            "9MK4iSrWZNvzzOS/gQKBgQDbHA9645AW2ple0w4rKHsoTsKNVJ0np5k+tHeOMfJBO248ZshXFb8W7L7uAfZdktOWKh//UZn5n2MY8iJGoj" +
            "PhmKXZqwy5WMrGBbef+VufhPR7kfm0OfpDPdswPre3OkOmDYKB/hLoNY3CXqOrjvFxuA29uX0/PkGDuUFjgmnkjQKBgQCwS20YKvqRuBC" +
            "DOeSNnaVF71tmcJpULaEIy/Xd/j5Ll0um1EbIT6xEYNoIVmv5w+sxt1LA7oJH7ElASuZwsBzij7m/VU3FS11ilZ7BoiL/T0eq1gRvZo6ge" +
            "0fMcOoRsUvhjF0p25m3qrU7JsSGzZWIC7fIKkvmnqaowesIImEwuQKBgGWzIZBFnbSt5DA6QEJR+NAdtnnJA2TLivqq4y8yCaKzhhgtEOIy" +
            "6s4mNvzcFlEafHEfvlhQtOpDS8jhYn1sfi5BHwHoPfCtwPim4LM1F4z0NlJA0CkSHBIn3NwI0FcQiSPC84eIuZfke5bzPH69y12gM2iTdw" +
            "nqyJfuXeZlOudlAoGBAKqqGg6PbMqsea/P16YQuvKc6Mqer4hWM9KkQHyLqBrdeGskjPFVz9rSN8BiiVzdgU47vmzveEfQQolu+O6WTyA1K" +
            "whCuGsKrnjFh07Ee0TVpveG//woOGK7daJAnRhLnr3WeEoRQRpAs/lzakVluCBf16Z668dyRryQHcteHOBhAoGAXX+7YaCOplGTslgKVo8M" +
            "bg8rGeqMNNCNTqwsY9gbI8dybepnh4PjrVc7hHgByiRqK+hHZDPK7T0euVv03IRr2Whc+CYIRUUdvnx1NFcXnDquNLJjpyy6iAclQR42laz" +
            "bOwmW3R6oQD2OCdm4L/qcndB4aOLN73cSl8IGzTBdnUk=";
    // 支付宝公钥
    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmnLC7aHotACmFcnJuaRxrPp0n/Ules5p67eSjgKtP" +
            "lk9z5eMA+Ej9fF9bMkes1EBskDN1cEzifc4tPv7hQi8dhfggN3MVUvcL921dW22ODwdZ1pyExaXK1thX75IbdMTOMhowb3UOiE+K7Vb" +
            "z/3DFO8gClIdX6OETPPadSFeqiqjo6jUBdBeoLogqBgxWfxeb/j7+qOKyLlqCqIGS8CR5Iq4vPR+39eortJBcJc2soKxCXtBzA4AWl5m" +
            "XTNevkfuuqG0fUH4MfgRu0xpWCfUMRk/O0Y78/m998wLw3vkvXnUnKxpHA5d/qjoT6/dSXx/Ac064ugnEU9pLe5rRsAlxQIDAQAB";
    String CHARSET = "utf-8";
    String serverUrl = "https://openapi.alipaydev.com/gateway.do"; // 正式"https://openapi.alipay.com/gateway.do"

    @GetMapping("/alipaytest")
    public void alipaytest(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws ServletException, IOException {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,
                APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
//        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        // 填充业务参数
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"20150320010109003\"," +
                " \"total_amount\":\"88.88\"," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


}
