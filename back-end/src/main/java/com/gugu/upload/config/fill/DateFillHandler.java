package com.gugu.upload.config.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

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
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
