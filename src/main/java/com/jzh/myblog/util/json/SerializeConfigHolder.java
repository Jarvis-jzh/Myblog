package com.jzh.myblog.util.json;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.lang.reflect.Type;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/29 14:09
 * @description
 */
public class SerializeConfigHolder {
    private static final SerializeConfig serializeConfig = new SerializeConfig();

    public static void addConfig(Type key, ObjectSerializer value) {
        serializeConfig.put(key, value);
    }

    public static SerializeConfig getConfig() {
        return serializeConfig;
    }
}
