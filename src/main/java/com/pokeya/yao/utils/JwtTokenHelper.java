package com.pokeya.yao.utils;

import com.pokeya.yao.dict.UserEnum;
import com.pokeya.yao.utils.beans.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mac
 */
public class JwtTokenHelper implements Serializable {

    private static final long serialVersionUID = 1579222883969867182L;
    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    private static final String OAUTH2_TOKEN_TYPE = "bearer";
    /**
     * 加密解密密钥
     * ws.dzyx.100:sn:sid md5加密后的前16位 9da20f0c4784d233
     */
    private static final String SECRET = "9da20f0c4784d233";

    private static final Long EXPIRATION_TIME = 15L;

    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(SECRET.getBytes())).parseClaimsJws(token).getBody();
    }

    public static Claims getAllClaims() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authHeader) && StringUtils.startsWith(authHeader, OAUTH2_TOKEN_TYPE)) {
            String authToken = StringUtils.substringAfter(authHeader, OAUTH2_TOKEN_TYPE).trim();
            JwtParser jwtParser = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(SECRET.getBytes()));
            return jwtParser.parseClaimsJws(authToken).getBody();
        }
        return null;
    }

    public static String getAccountFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public static Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(JwtUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UserEnum.BASE.getCode(), user);
        return doGenerateToken(claims, user.getAccount());
    }

    private static String doGenerateToken(Map<String, Object> claims, String account) {

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + Duration.ofDays(EXPIRATION_TIME).toSeconds());
        return Jwts.builder().setClaims(claims).setSubject(account).setIssuedAt(createdDate).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(SECRET.getBytes())).compact();
    }

    public static Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
