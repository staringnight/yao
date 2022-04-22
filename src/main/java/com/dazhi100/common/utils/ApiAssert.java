package com.dazhi100.common.utils;

import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.exception.ApiException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * api快速失败工具类
 */
public class ApiAssert {
    public static void isTrue(boolean b, ResultCode code) {
        ApiAssert.isTrue(b, code, null);
    }

    public static void isTrue(boolean b, ResultCode code, String msg) {
        if (!b) {
            throw new ApiException(code, msg);
        }
    }

    public static void isNotTrue(boolean b, ResultCode code) {
        ApiAssert.isTrue(!b, code);
    }

    public static void isNotTrue(boolean b, ResultCode code, String msg) {
        ApiAssert.isTrue(!b, code, msg);
    }

    public static <T> T isNotNull(T o, ResultCode code) {
        ApiAssert.isTrue(o != null, code);
        return o;
    }

    public static <T> T isNotNull(T o, ResultCode code, String msg) {
        ApiAssert.isTrue(o != null, code, msg);
        return o;
    }

    public static <T> void isNull(T o, ResultCode code) {
        ApiAssert.isTrue(o == null, code);
    }

    public static <T> void isNull(T o, ResultCode code, String msg) {
        ApiAssert.isTrue(o == null, code, msg);
    }

    /**
     * 字符串是否有长度
     *
     * @param s
     * @param code
     * @return
     */
    public static String hasLength(String s, ResultCode code) {
        ApiAssert.isTrue(StringUtils.hasLength(s), code);
        return s;
    }

    public static String hasLength(String s, ResultCode code, String msg) {
        ApiAssert.isTrue(StringUtils.hasLength(s), code, msg);
        return s;
    }

    /**
     * 字符串是否为全空字符串
     *
     * @param s
     * @param code
     * @return
     */
    public static String hasText(String s, ResultCode code) {
        ApiAssert.isTrue(StringUtils.hasText(s), code);
        return s;
    }

    public static String hasText(String s, ResultCode code, String msg) {
        ApiAssert.isTrue(StringUtils.hasText(s), code, msg);
        return s;
    }

    public static String contains(String part, String all, ResultCode code) {
        ApiAssert.isTrue(all.contains(part), code);
        return all;
    }

    public static String contains(String part, String all, ResultCode code, String msg) {
        ApiAssert.isTrue(all.contains(part), code, msg);
        return all;
    }

    public static String matches(String s, String regex, ResultCode code) {
        ApiAssert.isTrue(s.matches(regex), code);
        return s;
    }

    public static String matches(String s, String regex, ResultCode code, String msg) {
        ApiAssert.isTrue(s.matches(regex), code, msg);
        return s;
    }

    public static <O, T extends Collection<O>> T isNotEmpty(T c, ResultCode code) {
        ApiAssert.isTrue(!CollectionUtils.isEmpty(c), code);
        return c;
    }

    public static <O, T extends Collection<O>> T isNotEmpty(T c, ResultCode code, String msg) {
        ApiAssert.isTrue(!CollectionUtils.isEmpty(c), code, msg);
        return c;
    }

    public static <O, T extends Collection<O>> T isEmpty(T c, ResultCode code) {
        ApiAssert.isTrue(CollectionUtils.isEmpty(c), code);
        return c;
    }

    public static <O, T extends Collection<O>> T isEmpty(T c, ResultCode code, String msg) {
        ApiAssert.isTrue(CollectionUtils.isEmpty(c), code, msg);
        return c;
    }

    public static <T> T contains(T o, Collection<T> c, ResultCode code) {
        ApiAssert.isTrue(c.contains(o), code);
        return o;
    }

    public static <T> T contains(T o, Collection<T> c, ResultCode code, String msg) {
        ApiAssert.isTrue(c.contains(o), code, msg);
        return o;
    }
}
