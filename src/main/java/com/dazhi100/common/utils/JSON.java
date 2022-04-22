package com.dazhi100.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 **/
@Slf4j
public class JSON {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == String.class) {
            return (String) object;
        }
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("json序列化出错：" + object, e);
        }
        return null;
    }

    public static <T> T parseObject(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (Exception e) {
            log.error("json解析出错：" + content, e);
        }
        return null;
    }

    public static <T> T parseObject(String content, TypeReference<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (Exception e) {
            log.error("json解析出错：" + content, e);
        }
        return null;
    }

    public static <T> List<T> toList(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
            return getObjectMapper().readValue(content, javaType);
        } catch (Exception e) {
            log.error("json解析出错：" + content, e);
        }
        return null;
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, getObjectMapper().getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }


    public static <T> T nativeRead(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }


}
