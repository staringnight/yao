package com.pokeya.yao.utils.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王超
 * @date 2020/4/1 15:26
 */
@Getter
@AllArgsConstructor
public enum TemplateCode {
  NOTIFY_ENTER_FACE("SMS_166477087", "通知录脸"),
  NOTIFY_SCHOOL_DRIVER("SMS_172008595", "通知司机录脸"),
  VERIFICATION_CODE("SMS_80490011", "发送验证码"),
  NOTIFY_TEACHER_JOIN("SMS_175075550", "邀请教师加入"),
  NOTIFY_PARENT_JOIN("SMS_175075554", "邀请家长加入"),
  NOTIFY_DRAW_COUPON("SMS_187930786", "领取优惠券通知"),
  NOTIFY_BECOME_PARTNER("SMS_188631508", "成为营销合伙人"),
  NOTIFY_BECOME_PARTNER_AGAIN("SMS_188631503", "再次营销合伙人"),
  NOTIFY_CREATE_SCHOOL_BY_COUPON("SMS_189017877", "优惠活动创建学校"),
  ;

  private String code;
  private String desc;
}
