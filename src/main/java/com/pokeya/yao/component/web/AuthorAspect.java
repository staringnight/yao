package com.pokeya.yao.component.web;


import com.pokeya.yao.annotation.AuthCheck;
import com.pokeya.yao.bean.Result;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.dict.RoleEnum;
import com.pokeya.yao.utils.ApiAssert;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class AuthorAspect {

    @Pointcut("execution(@(@com.pokeya.yao.annotation.AuthCheck *) * *(..))")
    public void authCheckPoint() {
    }

    @Pointcut("@annotation(com.pokeya.yao.annotation.AuthCheck)")
    public void authCheckDirect() {

    }

    @Around(value = "authCheckPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();

        AuthCheck authCheck = AnnotationUtils.findAnnotation(signature.getMethod(), AuthCheck.class);
        ApiAssert.isNotNull(authCheck, ResultCode.COMMON_UNAUTHOR);
        return around(pjp, authCheck);
    }

    @Around(value = "@annotation(authCheck)")
    public Object around(ProceedingJoinPoint pjp, AuthCheck authCheck) throws Throwable {
        String token = getToken();
        ApiAssert.hasLength(token, ResultCode.COMMON_UNAUTHOR);

        int role = -1;//todo tokenUtil.getRole()

        for (RoleEnum roleEnum : authCheck.roleAuth()) {
            if (roleEnum.getId() == role) {
                return pjp.proceed();
            }
        }
        return Result.error(ResultCode.COMMON_UNAUTHOR);
    }

    private String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest().getHeader("token");
        }
        return null;
    }

}
