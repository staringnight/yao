package com.pokeya.yao.annotation;

import com.pokeya.yao.constant.ResultCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 只返回原始数据，防止数据统一响应处理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ErrorCode {
    ResultCode value() default ResultCode.COMMON_PARAMS_ERROR;

    String msg() default "";
}
