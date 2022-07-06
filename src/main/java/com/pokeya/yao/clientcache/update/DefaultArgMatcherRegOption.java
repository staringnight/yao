package com.pokeya.yao.clientcache.update;

import cn.hutool.core.util.ArrayUtil;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.constant.TimeConstant;
import com.pokeya.yao.exception.ApiException;
import com.pokeya.yao.exception.EnumException;
import com.pokeya.yao.utils.ApiAssert;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.time.LocalDate;

/**
 * 单个key解析时，使用此类来划分策略组，当需要一个reg解析成多个key时，请使用handler
 */
@Slf4j
@Getter//只提供getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)//私有修饰，防止外部调用
public enum DefaultArgMatcherRegOption implements ArgMatcherRegOption {
    /**
     * blank
     */
    blank("_") {
        @Override
        public String find(JoinPoint joinPoint, String field, String model) {
            return "_";
        }
    },
    /**
     * 直接取参数名对应的值，需要参数名匹配CacheField
     */
    directArg("d") {
        @Override
        public String find(JoinPoint joinPoint, String field, String model) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();

            // 获取field的下标
            int index = ArrayUtil.indexOf(parameterNames, field);
            ApiAssert.isTrue(index > -1, ResultCode.COMMON_CLIENT_CACHE_ERROR, "do not have field " + field);
            Object[] args = joinPoint.getArgs();
            Object obj = args[index];
            return switch (obj) {
                case LocalDate date -> date.format(TimeConstant.UniformDateFormatter);
                default -> String.valueOf(obj);
            };
        }
    },
    /**
     * 取model的name 反射取值, 注意走get方法，返回时自动toString
     */
    searchArg("s") {
        @Override
        public String find(JoinPoint joinPoint, String field, String model) {

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();
            // 获取field的下标
            int index = ArrayUtil.indexOf(parameterNames, model);
            ApiAssert.isTrue(index > -1, ResultCode.COMMON_CLIENT_CACHE_ERROR, "do not have field " + field);
            Object[] args = joinPoint.getArgs();
            Object arg = args[index];
            ApiAssert.isTrue(arg != null, ResultCode.COMMON_CLIENT_CACHE_ERROR, "arg is null");
            Class<?> aClass = arg.getClass();
            String key = aClass.getName() + "#" + field;
            try {
                Method fieldMethod = cache.get(key);
                fieldMethod.setAccessible(true);
                Object obj = fieldMethod.invoke(arg);
                return switch (obj) {
                    case LocalDate date -> date.format(TimeConstant.UniformDateFormatter);
                    default -> String.valueOf(obj);
                };
            } catch (Exception e) {
                log.error("find error", e);
                throw new ApiException(ResultCode.COMMON_CLIENT_CACHE_ERROR, "searchArg error");
            }
        }
    }
//    ,
//    //根据情况回表
//    condition("c") {
//        @Override
//        public String find(JoinPoint joinPoint, String field) {
//            xxxservice.findCidbyId();
//            return cid;
//        }
//    }
    ;
    private final String reg;

    private static final LoadingCache<String, Method> cache = Caffeine.newBuilder().maximumSize(4096).initialCapacity(4096).build(key -> {
        String[] split = key.split("#");
        Class<?> aClass = DefaultArgMatcherRegOption.class.getClassLoader().loadClass(split[0]);
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("get" + split[1])) {
                log.info("find method {},{}", split[0], split[1]);
                return method;
            }
        }
        throw new ApiException(ResultCode.COMMON_CLIENT_CACHE_ERROR, "searchArg error");
    });

    public static DefaultArgMatcherRegOption getByReg(String reg) {
        for (DefaultArgMatcherRegOption options : DefaultArgMatcherRegOption.values()) {
            if (options.getReg().equals(reg)) {
                return options;
            }
        }
        throw new EnumException("错误的argReg类型");
    }


}
