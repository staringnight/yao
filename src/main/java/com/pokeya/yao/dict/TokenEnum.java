package com.pokeya.yao.dict;

/**
 * @author mac
 */
public enum TokenEnum {
    /**
     * 权限
     */
    PERMISSION("permission"),
    /**
     * 基础信息
     */
    BASE("base"),
    ;
    private String code;

    TokenEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
