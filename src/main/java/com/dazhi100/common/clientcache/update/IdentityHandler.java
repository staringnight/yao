package com.dazhi100.common.clientcache.update;

import com.dazhi100.common.clientcache.EtagStoreManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 不对key进行任何改造，直接按key进行更新，通用的handler，匹配所有情况，默认优先级最低
 */
@Order(99)
@Component
@Slf4j
public class IdentityHandler implements UpdateClientCacheHandler {

    private EtagStoreManager storeManager;

    public IdentityHandler(@Autowired EtagStoreManager storeManager) {
        this.storeManager = storeManager;
    }

    @Override
    public boolean match(String key) {
        return true;
    }

    @Override
    public void handler(String key, JoinPoint joinPoint, Object returnValue) {
        try {
            storeManager.del(key);
        } catch (Exception e) {
            log.error("IdentityHandler error", e);
        }
    }
}
