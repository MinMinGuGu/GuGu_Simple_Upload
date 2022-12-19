package com.gugu.upload.component.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gugu.upload.common.exception.UnknownException;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * The type Date fill handler.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Component
public class DateFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        handleCreateTime(metaObject);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        handleCreateTime(metaObject);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    private void handleCreateTime(MetaObject metaObject) {
        String createTimeField = "createTime";
        Class<?> createTimeType = metaObject.getSetterType(createTimeField);
        if (Date.class.isAssignableFrom(createTimeType)) {
            this.strictInsertFill(metaObject, createTimeField, Date.class, new Date());
        } else {
            if (LocalDateTime.class.isAssignableFrom(createTimeType)) {
                this.strictInsertFill(metaObject, createTimeField, LocalDateTime.class, LocalDateTime.now());
            } else {
                throw new UnknownException("The type of the field createTime is unknown and cannot be auto populated.");
            }
        }
    }
}
