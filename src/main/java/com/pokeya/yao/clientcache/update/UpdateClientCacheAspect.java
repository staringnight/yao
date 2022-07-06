package com.pokeya.yao.clientcache.update;

import com.pokeya.yao.annotation.UpdateClientCache;
import com.pokeya.yao.annotation.UpdateClientCaches;
import com.pokeya.yao.clientcache.ClientCacheConfigBean;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.utils.ApiAssert;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Aspect
@Slf4j
public class UpdateClientCacheAspect {
    @Resource
    List<UpdateClientCacheHandler> handlers;

    @Pointcut("@annotation(com.pokeya.yao.annotation.UpdateClientCache)")
    public void updateClientCache() {
    }

    @Pointcut("@annotation(com.pokeya.yao.annotation.UpdateClientCaches)")
    public void updateClientCaches() {
    }

    @AfterReturning(value = "@annotation(updateClientCaches)", returning = "returnValue")
    public void doAfterReturningCaches(JoinPoint joinPoint, Object returnValue, UpdateClientCaches updateClientCaches) {
        UpdateClientCache[] value = updateClientCaches.value();
        for (UpdateClientCache updateClientCache : value) {
            doAfterReturning(joinPoint, returnValue, updateClientCache);
        }
    }

    @AfterReturning(value = "@annotation(updateClientCache)", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue, UpdateClientCache updateClientCache) {
        try {
            ArgMatcherRegOption[] argRegOptions = updateClientCache.argRegOptions().getEnumConstants();
            String argReg = updateClientCache.argReg();
            String keyConfig = updateClientCache.keyReg();
            String key = ClientCacheConfigBean.expressKey(keyConfig, argReg, argRegOptions, joinPoint);
            ApiAssert.hasLength(key, ResultCode.COMMON_CLIENT_CACHE_ERROR);
            handlers.stream()
                    .filter(h -> h.match(key))
                    .forEach(h -> h.handler(key, joinPoint, returnValue));
        } catch (Exception e) {
            log.error("update client cache error", e);
            //TODO: 通知处理
        }
    }

}
