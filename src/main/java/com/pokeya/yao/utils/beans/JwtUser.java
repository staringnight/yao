package com.pokeya.yao.utils.beans;

import lombok.Data;

/**
 * @author mac
 */
@Data
public class JwtUser {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
}
