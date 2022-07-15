package com.pokeya.yao.component.web;

import com.pokeya.yao.utils.JwtTokenHelper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.Arrays;

@Configuration
public class CommonSwaggerConfig {
    public static final String SWAGGER_VERSION = "1.0.0";
    public static final String SWAGGER_TITLE = "破壳鸭 SERVER API";
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI(@Value("${spring.application.name}") String application) {
        return new OpenAPI()
                .info(new Info()
                        .title(SWAGGER_TITLE)
                        .description(MessageFormat.format("破壳鸭 {0} API.", application))
                        .version(SWAGGER_VERSION))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme().name(SECURITY_SCHEME_NAME).type(SecurityScheme.Type.HTTP)
                                        .scheme(JwtTokenHelper.OAUTH2_TOKEN_TYPE)
                                        .bearerFormat(JwtTokenHelper.BEARER_FORMAT)))
                .servers(Arrays.asList(new Server().url("/" + application)));
    }
}
