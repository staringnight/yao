package com.pokeya.yao.component.web;

import com.pokeya.yao.annotation.ErrorCode;
import com.pokeya.yao.bean.Result;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;

@Order(-1)
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(HttpServlet.class)
public class WebExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public Result<String> APIExceptionHandler(ApiException e) {
        return Result.error(e.getResultCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = null;
        try {
            field = parameterType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ee) {
            log.error("NoSuchField", e);
            throw new ApiException();
        }
        // 获取Field对象上的自定义注解
        ErrorCode annotation = field.getAnnotation(ErrorCode.class);

        // 有注解的话就返回注解的响应信息
        if (annotation != null) {
            return Result.error(annotation.value(), StringUtils.hasText(annotation.msg()) ? annotation.msg() : defaultMessage);
        }

        // 没有注解就提取错误提示信息进行返回统一错误码
        return Result.error(ResultCode.COMMON_PARAMS_ERROR, defaultMessage);
    }

    @ExceptionHandler(BindException.class)
    public Result<String> MissingServletRequestParameterBindExceptionHandler(BindException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.error(ResultCode.COMMON_PARAMS_ERROR, "请求参数错误：" + defaultMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return Result.error(ResultCode.COMMON_PARAMS_ERROR, "缺少请求参数：" + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        return Result.error(ResultCode.COMMON_PARAMS_ERROR, "参数类型错误(" + e.getName() + "=" + e.getValue() + ")：" + e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return Result.error(ResultCode.COMMON_HTTP_METHOD_ERROR);
    }

}
