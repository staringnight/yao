package com.pokeya.yao.component.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author mac
 */
@Configuration
public class RedisConfig {

    @SuppressWarnings("all")
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisSerializer = new StringRedisTemplate(factory);
        return stringRedisSerializer;
    }

}
