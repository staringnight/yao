package com.pokeya.yao.component.infrastructure;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SnowflakeConfig {

    @Lazy
    @Bean
    public Snowflake snowflake(@Value("{snow.wokerId}") int wokerId, @Value("{snow.datacenterId}") int datacenterId) {
        return IdUtil.getSnowflake(wokerId, datacenterId);
    }

}
