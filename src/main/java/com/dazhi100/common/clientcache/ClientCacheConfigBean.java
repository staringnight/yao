package com.dazhi100.common.clientcache;

import com.dazhi100.common.clientcache.query.PathMatcherRegOptions;
import com.dazhi100.common.clientcache.update.ArgMatcherRegOption;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.utils.ApiAssert;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.server.ServerHttpRequest;

import java.util.Arrays;

import static com.dazhi100.common.clientcache.CacheField.OTHER;


@Data
@AllArgsConstructor
public class ClientCacheConfigBean {
    /**
     * key sid cid stuId customize
     * key:资源key
     * sid/cid/stuId 使用_（无需），?(变量，根据pathReg规则获取)
     * customize 使用_（无需），或指定参数名称，如 uid、msgId等，默认使用'?'的处理方式，但是会取keyReg指定的参数
     */
    String keyConfig;

    /**
     * keyMatcher sid cid stuId customize
     * keyMatcher：匹配对应的key，如：通过url路径正则匹配到的都关联到这个keyConfig下
     * sid/cid/stuId/customize 使用_(无需)，t(从token里拿)，q（从queryString里拿）
     */
    String matcherConfig;


    public static String expressKey(String keyConfig, String matcherConfig, ServerHttpRequest request) {
        StringBuilder key = new StringBuilder(getSourceRule(keyConfig));

        for (CacheField cacheField : CacheField.values()) {
            key.append(fill(keyConfig, matcherConfig, request, cacheField));
        }
        return key.toString();
    }

    public static String expressKey(String keyConfig, String matcherConfig, ArgMatcherRegOption[] argMatcherRegOptions, JoinPoint joinPoint) {
        StringBuilder key = new StringBuilder(getSourceRule(keyConfig));

        for (CacheField cacheField : CacheField.values()) {
            key.append(fill(keyConfig, matcherConfig, argMatcherRegOptions, joinPoint, cacheField));
        }
        return key.toString();
    }

    public static String fill(String keyConfig, String matcherConfig, ServerHttpRequest request, CacheField cacheField) {
        return getKeyRule(keyConfig, cacheField.getIndex())
                .fill(getPathRegRule(matcherConfig, cacheField.getIndex())
                        .find(request,
                                cacheField == OTHER ? getRule(keyConfig, OTHER.getIndex()) : cacheField.getField()));
    }

    public static String fill(String keyConfig, String matcherConfig, ArgMatcherRegOption[] argRegOptions, JoinPoint joinPoint, CacheField cacheField) {
        String rule = getRule(matcherConfig, cacheField.getIndex());
        String field = cacheField == OTHER ? getRule(keyConfig, cacheField.getIndex()) : cacheField.getField();
        String fillString = Arrays.stream(argRegOptions)
                .filter(a -> a.match(rule))
                .findFirst()
                .map(a -> a.find(joinPoint, field))
                .orElse(null);
        return getKeyRule(keyConfig, cacheField.getIndex()).fill(fillString);
    }

    public static KeyRegOption getKeyRule(String keyConfig, int i) {
        return KeyRegOption.getByReg(getRule(keyConfig, i));
    }

    public static PathMatcherRegOptions getPathRegRule(String matcherConfig, int i) {
        return PathMatcherRegOptions.getByReg(getRule(matcherConfig, i));
    }

    public static String getSourceRule(String config) {
        return getRule(config, 0);
    }

    public static String getRule(String config, int index) {
        String[] configArray = config.split(" ");
        ApiAssert.isTrue(configArray.length == 5, ResultCode.COMMON_CLIENT_CACHE_ERROR);
        ApiAssert.isTrue(index > -1 && index < 5, ResultCode.COMMON_CLIENT_CACHE_ERROR);
        return configArray[index];
    }
}
