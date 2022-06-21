package com.dazhi100.common.clientcache.update;

import com.dazhi100.common.clientcache.MatcherRegOption;
import org.aspectj.lang.JoinPoint;

public interface ArgMatcherRegOption extends MatcherRegOption {
    /**
     * 匹配参数
     * @param joinPoint 切点
     * @param field model或者参数的属性名
     * @param model 取model的name _默认
     * @return  真实值
     */
    String find(JoinPoint joinPoint, String field, String model);

    default boolean match(String opt) {
        return getReg().equals(opt);
    }
}
