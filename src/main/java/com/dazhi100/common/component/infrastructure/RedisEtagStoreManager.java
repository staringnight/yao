package com.dazhi100.common.component.infrastructure;

import com.dazhi100.common.clientcache.EtagStoreManager;
import org.springframework.stereotype.Component;

/**
 * expire 7 day + 随机24小时内任意秒数，
 * （每次get更新过期时间，无key创建---需前置调用get方法时、get方法创建key时均校验key的正确性，不能随意创建）
 * （update不更新时间，无key不新建），自动淘汰非热点key，根据线上占用空间实际调整
 */
@Component
public class RedisEtagStoreManager implements EtagStoreManager {
    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String update(String key) {
        return null;
    }

    @Override
    public boolean del(String key) {
        return false;
    }
}
