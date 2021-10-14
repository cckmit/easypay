package ink.breakpoint.easypay.merchant.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS服务客户端配置类
 */
@Configuration
public class OssConfig {

    @Value("${oss.aliyun.endpoint}")
    private String endpoint;

    @Value("${oss.aliyun.accessKey_id}")
    private String accessKey_id;

    @Value("${oss.aliyun.accessKey_secret}")
    private String accessKey_secret;

    @Bean
    public OSS oss(){
        return new OSSClientBuilder().build(endpoint,accessKey_id,accessKey_secret);
    }
}
