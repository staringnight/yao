package com.dazhi100.common.component.web;

import com.dazhi100.common.annotation.GetMappingWithClientCache;
import com.dazhi100.common.clientcache.ClientCacheConfigBean;
import com.dazhi100.common.clientcache.EtagStoreManager;
import com.dazhi100.common.clientcache.query.ClientCacheQueryMatcher;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.utils.ApiAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ClientCacheResponseAdvice implements ResponseBodyAdvice<Object> {

    private ClientCacheQueryMatcher clientCacheConfig;
    private EtagStoreManager storeManager;

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
        ClientCacheConfigBean config = clientCacheConfig.getKeyConfigFromPath(request.getURI().getPath());
        String keyConfig = config.getKeyConfig();
        String matcherConfig = config.getMatcherConfig();

        String realKey = ClientCacheConfigBean.expressKey(keyConfig, matcherConfig, request);
        ApiAssert.hasLength(realKey, ResultCode.COMMON_CLIENT_CACHE_ERROR);
        String Etag = storeManager.get(realKey);
        ApiAssert.hasLength(Etag, ResultCode.COMMON_CLIENT_CACHE_ERROR);
        response.getHeaders().add("Etag", Etag);
        return data;
    }
}

