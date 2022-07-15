package com.pokeya.yao.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
@ConditionalOnClass(HttpServlet.class)
public class SecurityUtil {

    private JwtParser jwtParser = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(JwtTokenHelper.SECRET.getBytes()));

    public Claims getAllClaims() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authHeader) && StringUtils.startsWith(authHeader, JwtTokenHelper.OAUTH2_TOKEN_TYPE)) {
            String authToken = StringUtils.substringAfter(authHeader, JwtTokenHelper.OAUTH2_TOKEN_TYPE).trim();
            return jwtParser.parseClaimsJws(authToken).getBody();
        }
        return null;
    }
}
