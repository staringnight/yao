package com.dazhi100.common.clientcache;

import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.utils.ApiAssert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter//只提供getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)//私有修饰，防止外部调用
public enum KeyRegOption {

    BLANK("_") {
        @Override
        public String fill(String arg) {
            return "_";
        }
    },
    VARIABLE("?") {
        @Override
        public String fill(String arg) {
            ApiAssert.hasLength(arg, ResultCode.COMMON_CLIENT_CACHE_ERROR, "key is variable, fill is empty");
            ApiAssert.isNotTrue("_".equals(arg), ResultCode.COMMON_CLIENT_CACHE_ERROR, "key is variable, fill is _");
            return arg;
        }
    };
    private final String reg;

    public abstract String fill(String arg);


    public static KeyRegOption getByReg(String reg) {
        for (KeyRegOption options : KeyRegOption.values()) {
            if (options.getReg().equals(reg)) {
                return options;
            }
        }
        return VARIABLE;
    }
}
