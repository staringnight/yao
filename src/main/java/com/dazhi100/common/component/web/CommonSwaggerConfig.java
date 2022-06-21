package com.dazhi100.common.component.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@EnableOpenApi
@Configuration
@ConditionalOnClass(Docket.class)
@Profile({"develop", "local"})
public class CommonSwaggerConfig {
    public static final String SWAGGER_VERSION = "1.0.0";
    public static final String SWAGGER_TITLE = "大智云校 SERVER API";

    @Bean
    public Docket docket(@Value("${swagger.enable}") boolean enable, @Value("${swagger.basePackage}") String basePackage) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(enable)
                .groupName("DZYX")
                .securityContexts(Collections.singletonList(SecurityContext.builder()
                        .securityReferences(Collections.singletonList(SecurityReference.builder()
                                .reference("Authorization")
                                .scopes(new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})
                                .build()))
                        .operationSelector(selector -> selector.requestMappingPattern().contains("login"))
                        .build()))
                .securitySchemes(Collections.singletonList(new ApiKey("Authorization", "token", "header")))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(SWAGGER_TITLE)
                .version(SWAGGER_VERSION)
                .build();
    }
}
