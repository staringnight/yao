package com.pokeya.yao.component.web;

import com.pokeya.yao.bean.Result;
import com.pokeya.yao.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;

/**
 * 全局异常处理，优先级最低，优先处理细粒度异常
 */
@Order(99)
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(HttpServlet.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> ExceptionHandler(Exception e) {
        log.error("error", e);
        return Result.error(ResultCode.COMMON_API_FAIL);
    }
}
