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
    private String id;
    /**
     * 账号
     */
    private String account;
}
