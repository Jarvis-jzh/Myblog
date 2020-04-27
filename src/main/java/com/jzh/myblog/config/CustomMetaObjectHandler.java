package com.jzh.myblog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/12 22:21
 * @description 注入公共字段
 */
public class CustomMetaObjectHandler implements MetaObjectHandler {
    private Logger logger = LoggerFactory.getLogger(CustomMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Object createTime = getFieldValByName("createTime", metaObject);
            Object updateTime = getFieldValByName("updateTime", metaObject);
            Object delTag = getFieldValByName("deleted", metaObject);

            Date date = new Date();
            if (null == createTime) {
                setFieldValByName("createTime", date, metaObject);
            }
            if (null == updateTime) {
                setFieldValByName("updateTime", date, metaObject);
            }
            if (null == delTag) {
                setFieldValByName("deleted", 0, metaObject);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
//            Object updateTime = getFieldValByName("updateTime", metaObject);
//            if (null == updateTime) {
            setFieldValByName("updateTime", new Date(), metaObject);
//            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }


}
