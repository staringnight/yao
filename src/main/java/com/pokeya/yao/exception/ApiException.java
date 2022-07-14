package com.pokeya.yao.exception;

import com.pokeya.yao.constant.CodeEnum;
import com.pokeya.yao.constant.ResultCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class ApiException extends RuntimeException {
    private CodeEnum resultCode;

    /**
     * 默认为接口异常
     */
    public ApiException() {
        this(ResultCode.COMMON_API_FAIL);
    }

    /**
     * 默认为接口异常，覆盖默认msg
     *
     * @param msg 覆盖默认msg
     */
    public ApiException(String msg) {
        this(ResultCode.COMMON_API_FAIL, msg);
    }

    /**
     * 使用自定义返回码
     *
     * @param resultCode
     */
    public ApiException(CodeEnum resultCode) {
        this(resultCode, null);
    }

    /**
     * 使用自定义返回码，并覆盖默认msg
     *
     * @param resultCode
     * @param msg
     */
    public ApiException(CodeEnum resultCode, String msg) {
        super(StringUtils.hasLength(msg) ? msg : resultCode.getDesc());
        this.resultCode = resultCode;
    }

}
