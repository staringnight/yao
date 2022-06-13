package com.dazhi100.common.clientcache.update;

import com.dazhi100.common.annotation.UpdateClientCache;
import com.dazhi100.common.clientcache.ClientCacheConfigBean;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.utils.ApiAssert;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.Resource;
import java.util.List;

@Aspect
public class UpdateClientCacheAspect {
    @Resource
    List<UpdateClientCacheHandler> handlers;

    @Pointcut("@annotation(com.dazhi100.common.annotation.UpdateClientCache)")
    public void updateClientCache() {
    }


    @AfterReturning(value = "@annotation(updateClientCache)", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue, UpdateClientCache updateClientCache) {

        ArgMatcherRegOption[] argRegOptions = updateClientCache.argRegOptions().getEnumConstants();
        String argReg = updateClientCache.argReg();
        String keyConfig = updateClientCache.keyReg();
        String key = ClientCacheConfigBean.expressKey(keyConfig, argReg, argRegOptions, joinPoint);
        ApiAssert.hasLength(key, ResultCode.COMMON_CLIENT_CACHE_ERROR);
        handlers.stream()
                .filter(h -> h.match(key))
                .findFirst()
                .ifPresent(h -> h.handler(key, joinPoint, returnValue));
    }

}
