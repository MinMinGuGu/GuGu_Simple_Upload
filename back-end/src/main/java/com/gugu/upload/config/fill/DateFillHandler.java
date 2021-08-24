package com.gugu.upload.config.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
    }
}
