package com.pokeya.yao.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 脱敏工具
 */
public class DataDesensitizedUtil {

    /**
     * 【手机号码】前三位，后4位，其他隐藏，比如135****2210
     *
     * @param num 移动电话
     * @return 脱敏后的移动电话
     */
    public static String phone(String num) {
        if (StrUtil.isBlank(num)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.hide(num, 3, num.length() - 4);
    }
}
