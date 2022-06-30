package com.dazhi100.common.utils;

import com.dazhi100.common.utils.interceptor.HttpLoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;

/**
 * 简单实现
 * HttpUtil
 **/
@Slf4j
public class HttpUtil {
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    public static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor())
            .connectionPool(new ConnectionPool())
            .connectTimeout(Duration.ofSeconds(10)).readTimeout(Duration.ofMinutes(1)).writeTimeout(Duration.ofMinutes(1)).build();

    public static String post(String url, Object object) throws IOException {
        RequestBody body = RequestBody.create(JSON.toJSONString(object), MEDIA_TYPE);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
