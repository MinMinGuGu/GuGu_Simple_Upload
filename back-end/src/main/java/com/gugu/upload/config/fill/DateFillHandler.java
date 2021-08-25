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
        // FIXME: 2021/8/25 23:50 minmin 自动填充在数据库上的时间不对 相差了8小时
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // FIXME: 2021/8/25 23:50 minmin 自动填充在数据库上的时间不对 相差了8小时
        this.strictUpdateFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
