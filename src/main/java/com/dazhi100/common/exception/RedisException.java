package com.dazhi100.common.exception;

import com.dazhi100.common.constant.ResultCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author mac
 */
@Getter
public class RedisException extends Exception {
    private ResultCode resultCode;

    /**
     * 默认为接口异常
     */
    public RedisException() {
        this(ResultCode.COMMON_API_FAIL);
    }

    /**
     * 默认为接口异常，覆盖默认msg
     *
     * @param msg 覆盖默认msg
     */
    public RedisException(String msg) {
        this(ResultCode.COMMON_API_FAIL, msg);
    }

    /**
     * 使用自定义返回码
     *
     * @param resultCode
     */
    public RedisException(ResultCode resultCode) {
        this(resultCode, null);
    }

    /**
     * 使用自定义返回码，并覆盖默认msg
     *
     * @param resultCode
     * @param msg
     */
    public RedisException(ResultCode resultCode, String msg) {
        super(StringUtils.hasLength(msg) ? msg : resultCode.getDesc());
        this.resultCode = resultCode;
    }

}
