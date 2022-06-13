package com.dazhi100.common.clientcache.update;

import com.dazhi100.common.clientcache.EtagStoreManager;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 对 * 进行处理
 */
@Order(10)
@Component
public class MultiplyHandler implements UpdateClientCacheHandler {

    private EtagStoreManager storeManager;

    public MultiplyHandler(@Autowired EtagStoreManager storeManager) {
        this.storeManager = storeManager;
    }

    @Override
    public boolean match(String key) {
        return key.contains("*");
    }

    @Override
    public void handler(String key, JoinPoint joinPoint, Object returnValue) {
        //todo
    }
}
