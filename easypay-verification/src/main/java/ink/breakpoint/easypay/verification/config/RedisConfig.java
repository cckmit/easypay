package ink.breakpoint.easypay.verification.config;


import ink.breakpoint.easypay.verification.common.cache.Cache;
import ink.breakpoint.easypay.verification.common.cache.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
public class RedisConfig {
	
	@Bean
	public Cache cache(StringRedisTemplate redisTemplate){
		return new RedisCache(redisTemplate);
	}
	

}
