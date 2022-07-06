package com.pokeya.yao.annotation;

import com.pokeya.yao.clientcache.update.ArgMatcherRegOption;
import com.pokeya.yao.clientcache.update.DefaultArgMatcherRegOption;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(UpdateClientCaches.class)
public @interface UpdateClientCache {

    String keyReg();

    String argReg();

    Class<? extends ArgMatcherRegOption> argRegOptions() default DefaultArgMatcherRegOption.class;

}
