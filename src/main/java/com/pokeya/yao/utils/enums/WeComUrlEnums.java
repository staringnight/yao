package com.pokeya.yao.utils.enums;

/**
 * 企业微信推送url
 *
 * @author mac
 */
public enum WeComUrlEnums {
    /**
     * 敏捷试运行团队
     */
    AGILE_TEAM("agileTeam");
    /**
     * 编码
     */
    private String code;

    WeComUrlEnums(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
