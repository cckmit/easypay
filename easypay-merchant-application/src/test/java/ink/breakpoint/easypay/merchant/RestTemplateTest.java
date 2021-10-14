package ink.breakpoint.easypay.merchant;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testGet(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        String body = forEntity.getBody();
        log.info("获取网页内容：【{}】",body);
    }

    // 调用短信服务获取验证码
    @Test
    public void testGetMsgCode(){
        String url = "http://localhost:56085/sailing/generate?name=sms&effectiveTime=600";
        // 请求体
        Map<String, Object> body = new HashMap<>();
        body.put("mobile", "15446466454545");
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 封装 HttpEntity 对象
        HttpEntity entity = new HttpEntity(body, headers);
        // 调用短信服务
        /*
         * 参数1：服务地址
         * 参数2：请求方式
         * 参数3：请求体
         * 参数4：响应类型
         */
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        // 获取响应体
        Map entityBody = responseEntity.getBody();
        log.info("获取验证码：【{}】", entityBody);
        // 获取校验key
        Map result = (Map) entityBody.get("result");
        String key = result.get("key").toString();
        System.out.println("key：" + key);
    }

}
