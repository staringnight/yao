package com.dazhi100.common.component.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author mac
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(@Autowired LettuceConnectionFactory factory) {
        StringRedisTemplate stringRedisSerializer = new StringRedisTemplate(factory);
        return stringRedisSerializer;
    }

}
