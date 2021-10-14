package ink.breakpoint.easypay.transaction.config;

import ink.breakpoint.easypay.common.cache.Cache;
import ink.breakpoint.easypay.transaction.common.util.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class RedisConfig {

    @Bean
    public Cache cache(StringRedisTemplate stringRedisTemplate){
        return new RedisCache(stringRedisTemplate);
    }
}
