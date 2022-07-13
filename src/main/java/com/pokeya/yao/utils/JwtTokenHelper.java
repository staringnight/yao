package com.pokeya.yao.utils;

import com.pokeya.yao.dict.UserEnum;
import com.pokeya.yao.utils.beans.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
     * 加密解密密钥
     * ws.dzyx.100:sn:sid md5加密后的前16位 9da20f0c4784d233
     */
    private static final String SECRET = "9da20f0c4784d233";

    private static final Long EXPIRATION_TIME = 15L;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(SECRET.getBytes())).parseClaimsJws(token).getBody();
    }

    public String getAccountFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(JwtUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UserEnum.BASE.getCode(), user);
        return doGenerateToken(claims, user.getAccount());
    }

    private String doGenerateToken(Map<String, Object> claims, String account) {

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + Duration.ofDays(EXPIRATION_TIME).toSeconds());
        return Jwts.builder().setClaims(claims).setSubject(account).setIssuedAt(createdDate).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(SECRET.getBytes())).compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
