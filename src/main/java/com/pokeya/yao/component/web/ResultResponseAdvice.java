package com.pokeya.yao.component.web;

import com.pokeya.yao.annotation.NotWarpResponseBody;
import com.pokeya.yao.bean.Result;
import com.pokeya.yao.utils.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnClass(ResponseBodyAdvice.class)
@RestControllerAdvice
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {

    private static List<String> excludeUrlList = new ArrayList<>();

    static {
        excludeUrlList.add("/api-docs");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果接口返回的类型本身就是Result那就没有必要进行额外的操作，返回false
        // 如果方法上加了我们的自定义注解也没有必要进行额外的操作
        return !(returnType.getParameterType().equals(Result.class) || returnType.hasMethodAnnotation(NotWarpResponseBody.class));
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (excludeUrlList.stream().anyMatch(url -> request.getURI().getPath().contains(url))) {
            return data;
        }
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            // 将数据包装在Result里后，再转换为json字符串响应给前端
            return JSON.toJSONString(Result.success(data));
        }
        // 将原本的数据包装在Result里
        return Result.success(data);
    }
}

