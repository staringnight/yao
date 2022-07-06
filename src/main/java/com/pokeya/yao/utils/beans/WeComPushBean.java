package com.pokeya.yao.utils.beans;

/**
 * 企业微信model
 * @author mac
 * {
 *   "msgtype": "text",
 *   "text": {
 *     "content": "Hi，我是机器人测试\n由汪来兵于05月09日添加到群"
 *   }
 * }
 */
public class WeComPushBean {
    /**
     *类型  text  markdown image news
     */
    private String msgtype;
    /**
     * 内容
     */
    private WeComPushContentBean text;
    private WeComPushContentBean markdown;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public WeComPushContentBean getText() {
        return text;
    }

    public void setText(WeComPushContentBean text) {
        this.text = text;
    }

    public WeComPushContentBean getMarkdown() {
        return markdown;
    }

    public void setMarkdown(WeComPushContentBean markdown) {
        this.markdown = markdown;
    }
}


