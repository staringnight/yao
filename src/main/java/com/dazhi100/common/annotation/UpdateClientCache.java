package com.dazhi100.common.annotation;

import com.dazhi100.common.clientcache.update.ArgMatcherRegOption;
import com.dazhi100.common.clientcache.update.DefaultArgMatcherRegOption;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(UpdateClientCaches.class )
public @interface UpdateClientCache {

    String keyReg();

    String argReg();

    Class<? extends ArgMatcherRegOption> argRegOptions() default DefaultArgMatcherRegOption.class;


}
