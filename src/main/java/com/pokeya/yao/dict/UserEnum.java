package com.pokeya.yao.dict;

/**
 * @author mac
 */
public enum UserEnum {
    /**
     * 权限
     */
//    PERMISSION("permission"),
    /**
     * 基础信息
     */
    BASE("base"),
    ;
    private String code;

    UserEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
