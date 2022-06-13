package com.dazhi100.common.clientcache.update;

import com.dazhi100.common.clientcache.MatcherRegOption;
import org.aspectj.lang.JoinPoint;

public interface ArgMatcherRegOption extends MatcherRegOption {
    String find(JoinPoint joinPoint, String field);

    default boolean match(String opt) {
        return getReg().equals(opt);
    }
}
