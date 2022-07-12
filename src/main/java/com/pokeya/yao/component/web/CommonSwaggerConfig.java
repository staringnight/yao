package com.pokeya.yao.component.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
public class CommonSwaggerConfig {
    public static final String SWAGGER_VERSION = "1.0.0";
    public static final String SWAGGER_TITLE = "破壳鸭 SERVER API";

    @Bean
    public OpenAPI openAPI(@Value("${spring.application.name}") String application) {
        return new OpenAPI()
                .info(new Info()
                        .title(SWAGGER_TITLE)
                        .description(MessageFormat.format("破壳鸭 {0} API.", application))
                        .version(SWAGGER_VERSION));
    }
}
