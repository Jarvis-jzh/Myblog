package com.jzh.myblog.util.sms;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/25 13:15
 * @description 阿里云短信验证码工具类
 */
@Component
@Slf4j(topic = "aliyun-sms")
public class AliyunSmsUtils {

    @Autowired
    private AliyunSmsProperties properties;

    /**
     * 发送短信验证码
     *
     * @param phone     手机号
     * @return          发送的验证码
     */
    public Integer sendSms(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", properties.getAccessId(), properties.getAccessKey());
        IAcsClient client = new DefaultAcsClient(profile);

        int code = RandomUtil.randomInt(1001, 9999);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", properties.getSignName());
        request.putQueryParameter("TemplateCode", properties.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONUtil.parseObj(response.getData());
            System.out.println(jsonObject.toString());
            if ("OK".equals(jsonObject.getStr("Code"))) {
                return code;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer smsTest() {
        return 1111;
    }
}
