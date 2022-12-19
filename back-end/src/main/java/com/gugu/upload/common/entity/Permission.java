package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The type Permission.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/28
 * @since 1.8
 */
@Data
@Accessors(chain = true)
@TableName("permission")
public class Permission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Permission that = (Permission) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * The enum Permission enum.
     *
     * @author minmin
     * @version 1.0
     * @since 1.8
     */
    @Getter
    public enum PermissionEnum {
        /**
         * 全部权限
         */
        ALL(3, "all"),
        /**
         * 管理权限
         */
        MANAGE(2, "manage"),
        /**
         * 上传权限
         */
        UPLOAD(1, "upload");

        private final Integer score;

        private final String name;

        PermissionEnum(Integer score, String name) {
            this.score = score;
            this.name = name;
        }
    }
}
