package com.jzh.myblog.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.util.json.SerializeConfigHolder;
import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @title Result
 * @date 2020/2/4 18:22
 * @description 返回统一的响应格式
 */
@Data
public class Result<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 具体内容
     */
    private T data;

    public Result() {

    }

    public Result(Integer code, boolean success, String msg, T data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public String toJson() {
        SerializeConfig serializeConfig = SerializeConfigHolder.getConfig();
        if (serializeConfig == null) {
            return JSON.toJSONString(this,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteEnumUsingToString);
        } else {
            return JSON.toJSONString(this, serializeConfig,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteEnumUsingToString);
        }

        /*return JSONObject.toJSONString(this,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteEnumUsingToString);*/
    }
}
