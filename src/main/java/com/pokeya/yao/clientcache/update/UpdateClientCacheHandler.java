package com.pokeya.yao.clientcache.update;

import org.aspectj.lang.JoinPoint;

public interface UpdateClientCacheHandler {
    boolean match(String key);

    void handler(String key, JoinPoint joinPoint, Object returnValue);
}
