package com.pokeya.yao.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.exception.ApiException;
import com.pokeya.yao.utils.config.TemplateCode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
 * <p>
 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
 *
 * @version V1.0
 * @Author: Jack.Zou
 * @Date: 2018/11/6 9:58 AM
 */
@Slf4j
public class AliYunSendMsg {
    private static final String SMS_SIGNNAME = "大智云校";
    private static final String SMS_DEFAULT_POI = "cn-hangzhou";
    // 产品名称:云通信短信API产品,开发者无需替换
    private static final String PRODUCT = "Dysmsapi";
    // 产品域名,开发者无需替换
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String ACCESS_KEY_ID = "6lKEW8SAhE759guF";
    private static final String ACCESS_KEY_SECRET = "jY6EK3qL2JdwsMuvpzft6umy9XBsUc";

    public static SendSmsResponse sendSms(String phone, TemplateCode templateCode, String param) {

        SendSmsResponse sendSmsResponse = null;
        try {
            // 初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile(SMS_DEFAULT_POI, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            DefaultProfile.addEndpoint(SMS_DEFAULT_POI, PRODUCT, DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            // 必填:待发送手机号
            request.setPhoneNumbers(phone);
            // 必填:短信签名-可在短信控制台中找到
            request.setSignName(SMS_SIGNNAME);
            // 必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode.getCode());
            // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            // request.setTemplateParam("{\"code\":\"123456\"}");
            request.setTemplateParam(param);

            // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            // request.setSmsUpExtendCode("90997");

            // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            // request.setOutId("yourOutId");

            // hint 此处可能会抛出异常，注意catch
            sendSmsResponse = acsClient.getAcsResponse(request);

        } catch (Exception e) {
            log.error("Send Sms Message error ,{} , sendSmsResponse {}", e, JSON.toJSONString(sendSmsResponse));
            throw new ApiException(ResultCode.SEND_SMS_ERROR);
        }

        if (!"OK".equals(sendSmsResponse.getMessage())) {
            log.error("Send Sms Message failed , sendSmsResponse {}", JSON.toJSONString(sendSmsResponse));
            throw new ApiException(ResultCode.SEND_SMS_ERROR);
        }

        return sendSmsResponse;
    }

    public static QuerySendDetailsResponse querySendDetails(String phone, String bizId) throws ClientException {

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(SMS_DEFAULT_POI, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint(SMS_DEFAULT_POI, PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(phone);
        // 可选-流水号
        request.setBizId(bizId);
        // 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        // hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return querySendDetailsResponse;
    }
}
