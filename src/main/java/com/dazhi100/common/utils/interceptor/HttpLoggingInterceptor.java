package com.dazhi100.common.utils.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 简单实现
 */
@Slf4j
public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response proceed = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("HttpLoggingInterceptor-----" + request.url() + "-----End:" + duration + "毫秒----------");
        return proceed;
    }
}
