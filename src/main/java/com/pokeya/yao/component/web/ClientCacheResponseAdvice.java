package com.pokeya.yao.component.web;

import com.pokeya.yao.annotation.GetMappingWithClientCache;
import com.pokeya.yao.clientcache.ClientCacheConfigBean;
import com.pokeya.yao.clientcache.EtagStoreManager;
import com.pokeya.yao.clientcache.query.ClientCacheQueryMatcher;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.utils.ApiAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author mac
 */
@ConditionalOnClass(ResponseBodyAdvice.class)
@RestControllerAdvice
@Slf4j
public class ClientCacheResponseAdvice implements ResponseBodyAdvice<Object> {

    private ClientCacheQueryMatcher clientCacheConfig;
    private EtagStoreManager storeManager;
    @Value("${spring.application.name}")
    private String baseUrl;

    public ClientCacheResponseAdvice(@Autowired ClientCacheQueryMatcher clientCacheConfig, @Autowired EtagStoreManager storeManager) {
        this.clientCacheConfig = clientCacheConfig;
        this.storeManager = storeManager;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(GetMappingWithClientCache.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            String path = "/" + baseUrl + request.getURI().getPath();
            ClientCacheConfigBean config = clientCacheConfig.getKeyConfigFromPath(path);
            if (config == null) {
                log.warn("ClientCacheResponseAdvice warn 没有配置:{}", path);
                return data;
            }
            String keyConfig = config.getKeyConfig();
            String matcherConfig = config.getMatcherConfig();
            String realKey = ClientCacheConfigBean.expressKey(keyConfig, matcherConfig, request);
            ApiAssert.hasLength(realKey, ResultCode.COMMON_CLIENT_CACHE_ERROR);
            String Etag = storeManager.get(realKey);
            ApiAssert.hasLength(Etag, ResultCode.COMMON_CLIENT_CACHE_ERROR);
            response.getHeaders().set(HttpHeaders.ETAG, Etag);
        } catch (Exception e) {
            log.error("ClientCacheResponseAdvice error", e);
        }
        return data;
    }
}

