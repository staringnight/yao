package com.pokeya.yao.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pokeya.yao.constant.CodeEnum;
import com.pokeya.yao.constant.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@ToString
@Schema(name = "返回Result")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    @Schema(name = "状态码", description = "默认200是成功")
    private int responseCode;
    @Schema(name = "响应信息", description = "来说明响应情况")
    private String responseMessage;
    @Schema(name = "响应的具体数据")
    private T data;

    //----------------------------禁用构造方法，只能使用success or error--------------------------、、
    private Result(T data) {
        this(ResultCode.SUCCESS, null, data);
    }

    private Result(CodeEnum resultCode, String message) {
        this(resultCode, message, null);
    }

    private Result(CodeEnum resultCode, String message, T data) {
        this.responseCode = resultCode.getCode();
        this.responseMessage = StringUtils.hasLength(message) ? message : resultCode.getDesc();
        this.data = data;
    }

    //-----------------------------success or error-----------------------//

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeEnum resultCode) {
        return new Result<>(resultCode, null);
    }

    /**
     * 返回error 且覆盖默认error返回信息
     *
     * @param resultCode
     * @param message    覆盖信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeEnum resultCode, String message) {
        return new Result<>(resultCode, message);
    }

}
