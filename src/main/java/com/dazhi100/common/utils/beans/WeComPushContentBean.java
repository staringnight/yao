package com.dazhi100.common.utils.beans;

/**
 * 企业微信model
 * @author mac
 * {
 *     "msgtype": "text",
 *     "text": {
 *         "content": "广州今日天气：29度，大部分多云，降雨概率：60%",
 *         "mentioned_list":["wangqing","@all"],
 *         "mentioned_mobile_list":["13800001111","@all"]
 *     }
 * }
 */
public class WeComPushContentBean {
    /**
     * 文本内容，最长不超过2048个字节，必须是utf8编码
     * markdown内容，最长不超过4096个字节，必须是utf8编码
     * 消息类型，此时固定为image每个机器人发送的消息不能超过20条/分钟。
     */
    private String content;
    /**
     * userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人，如果开发者获取不到userid，可以使用mentioned_mobile_list
     */
    private String mentioned_list;
    /**
     * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
     */
    private String mentioned_mobile_list;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMentioned_list() {
        return mentioned_list;
    }

    public void setMentioned_list(String mentioned_list) {
        this.mentioned_list = mentioned_list;
    }

    public String getMentioned_mobile_list() {
        return mentioned_mobile_list;
    }

    public void setMentioned_mobile_list(String mentioned_mobile_list) {
        this.mentioned_mobile_list = mentioned_mobile_list;
    }
}


