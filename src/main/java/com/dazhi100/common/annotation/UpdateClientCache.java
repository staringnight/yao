package com.dazhi100.common.annotation;

import com.dazhi100.common.clientcache.update.ArgMatcherRegOption;
import com.dazhi100.common.clientcache.update.DefaultArgMatcherRegOption;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateClientCache {
    @AliasFor("keyReg")
    String value();

    @AliasFor("value")
    String keyReg();

    String argReg();

    Class<? extends ArgMatcherRegOption> argRegOptions() default DefaultArgMatcherRegOption.class;


}
