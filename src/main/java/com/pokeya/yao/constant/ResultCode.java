package com.pokeya.yao.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述: 消息通知枚举
 *
 * @version V1.0
 * @Author: Jack.Zou
 * @Date: 2018/10/19 11:53 AM
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResultCode {
    //-------------------------通用系统code----------------------------//
    //通用，接口内部异常，不直接显示给用户
    COMMON_API_FAIL(1000, "服务器繁忙，请稍后再试"),
    COMMON_PARAMS_ERROR(1001, "请求参数错误"),
    COMMON_HTTP_METHOD_ERROR(1002, "http请求方式错误"),
    COMMON_CLIENT_CACHE_ERROR(1003, "客户端缓存配置错误"),
    REDIS_ERROR(1004, "redis 操作失败"),
    COMMON_UNAUTHOR(1005, "权限不足"),
    SUCCESS(200, "成功");
    //-------------------------通用系统code----------------------------//

    private final Integer code;
    private final String desc;
}
