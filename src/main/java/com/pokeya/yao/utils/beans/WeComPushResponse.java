package com.pokeya.yao.utils.beans;

import lombok.Data;

/**
 * 企业微信model  {"errcode":0,"errmsg":"ok"}
 */
@Data
public class WeComPushResponse {
    private Integer errcode;
    private String errmsg;

}

