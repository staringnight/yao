package com.pokeya.yao.annotation;

import com.pokeya.yao.dict.RoleEnum;

import java.lang.annotation.*;

/**
 * @Author chentong
 * @Date 2022/7/12 6:44 PM
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    RoleEnum[] roleAuth() default RoleEnum.ADMIN;
}
