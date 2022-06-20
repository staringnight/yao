package com.dazhi100.common.clientcache.update;

import cn.hutool.core.util.ArrayUtil;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.exception.EnumException;
import com.dazhi100.common.utils.ApiAssert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 单个key解析时，使用此类来划分策略组，当需要一个reg解析成多个key时，请使用handler
 */
@Getter//只提供getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)//私有修饰，防止外部调用
public enum DefaultArgMatcherRegOption implements ArgMatcherRegOption {
    blank("_") {
        @Override
        public String find(JoinPoint joinPoint, String field) {
            return "_";
        }
    },
    directArg("d") {
        @Override
        public String find(JoinPoint joinPoint, String field) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();

            // 获取field的下标
            int index = ArrayUtil.indexOf(parameterNames, field);
            ApiAssert.isTrue(index > -1, ResultCode.COMMON_CLIENT_CACHE_ERROR, "do not have field " + field);
            Object[] args = joinPoint.getArgs();
            //// TODO: 2022/6/20  反射获取参数值
            return String.valueOf(args[index]);
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


    public static DefaultArgMatcherRegOption getByReg(String reg) {
        for (DefaultArgMatcherRegOption options : DefaultArgMatcherRegOption.values()) {
            if (options.getReg().equals(reg)) {
                return options;
            }
        }
        throw new EnumException("错误的argReg类型");
    }


}
