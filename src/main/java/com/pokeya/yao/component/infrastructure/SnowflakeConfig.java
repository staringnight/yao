package com.pokeya.yao.component.infrastructure;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Slf4j
@Configuration
public class SnowflakeConfig {

    private static final String workIdStr = NetUtil.getLocalhost().getHostAddress() + System.currentTimeMillis();

    @Lazy
    @Bean
    public Snowflake snowflake(@Value("${server.port}") int datacenterId) {
        Long wokerId = HashUtil.mixHash(workIdStr);
        log.info("SnowflakeConfig:{}:{}", wokerId, datacenterId);
        return IdUtil.getSnowflake(wokerId, datacenterId);
    }

}
