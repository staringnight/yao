package com.dazhi100.common.utils;

import com.dazhi100.common.utils.beans.WeComPushBean;
import com.dazhi100.common.utils.beans.WeComPushContentBean;
import com.dazhi100.common.utils.beans.WeComPushResponse;
import com.dazhi100.common.utils.enums.WeComContentEnums;
import com.dazhi100.common.utils.enums.WeComUrlEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 企业机器人通知
 * 每个机器人发送的消息不能超过20条/分钟。
 *
 * @author mac
 */
@Slf4j
@Component
public class WeComPushUtil {
    private Environment environment;

    @Autowired
    public WeComPushUtil(Environment environment) {
        this.environment = environment;
    }

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2, 1L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 企业机器人通知 同步
     */
    public void push(WeComUrlEnums weComUrlEnum, WeComContentEnums weComContentEnum, String... content) {
        push(weComUrlEnum, weComContentEnum, content, "@all");
    }


    /**
     * 企业机器人通知 同步
     *
     * @param mentionedMobileList 通知人的手机号
     */
    public void push(WeComUrlEnums weComUrlEnum, WeComContentEnums weComContentEnum, String[] contents, String mentionedMobileList) {
        try {
            WeComPushBean weComPushBean = new WeComPushBean();
            weComPushBean.setMsgtype("text");
            WeComPushContentBean weComPushContentBean = new WeComPushContentBean();
            weComPushBean.setText(weComPushContentBean);
            weComPushContentBean.setContent(MessageFormat.format(weComContentEnum.getContent(), contents));
            weComPushContentBean.setMentioned_mobile_list(mentionedMobileList);
            String url = environment.getRequiredProperty(MessageFormat.format("weCom.webhookUrl.{0}", weComUrlEnum.getCode()));
            log.info("企业机器人通知:{},{}", JSON.toJSONString(weComPushBean), url);
            String s = HttpUtil.post(url, weComPushBean);
            log.info("企业机器人结果：{}", s);
            //{"errcode":0,"errmsg":"ok"}
            if (JSON.parseObject(s, WeComPushResponse.class).getErrcode() != 0) {
                log.warn("企业机器人通知失败！");
            }
        } catch (Exception e) {
            log.error("企业机器人异常：{}", e.getMessage(), e);
        }
    }

    /**
     * 企业机器人通知 异步
     */
    public void asyncPush(WeComUrlEnums weComUrlEnum, WeComContentEnums weComContentEnum, String... content) {
        threadPoolExecutor.execute(() -> push(weComUrlEnum, weComContentEnum, content));
    }

    /**
     * 企业机器人通知 异步
     */
    public void asyncPush(WeComUrlEnums weComUrlEnum, WeComContentEnums weComContentEnum, String[] contents, String mentionedMobileList) {
        threadPoolExecutor.execute(() -> push(weComUrlEnum, weComContentEnum, contents, mentionedMobileList));
    }
}
