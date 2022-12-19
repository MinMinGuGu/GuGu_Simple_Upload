package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The type Record.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Data
@TableName("operation_log")
public class OperationLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String ipAddress;
    private OperationType operationType;
    private String userName;
    private String context;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * The enum Operation type.
     *
     * @author minmin
     * @version 1.0
     * @since 1.8
     */
    @Getter
    public enum OperationType {
        /**
         * 未知操作
         */
        UNKNOWN(0, "未知操作"),
        /**
         * 用户登录操作
         */
        LOGIN(1, "用户登录"),
        /**
         * 文件上传操作
         */
        FILE_UPLOAD(2, "文件上传"),
        /**
         * 文件删除操作
         */
        FILE_DELETE(3, "文件删除"),
        /**
         * 新增用户操作
         */
        USER_ADD(4, "新增用户"),
        /**
         * 修改用户操作
         */
        USER_UPDATE(5, "修改用户"),
        /**
         * 删除用户操作
         */
        USER_DELETE(6, "删除用户"),
        /**
         * 新增角色操作
         */
        ROLE_ADD(7, "新增角色"),
        /**
         * 修改角色操作
         */
        ROLE_UPDATE(8, "修改角色"),
        /**
         * 删除角色操作
         */
        ROLE_DELETE(9, "删除角色"),
        /**
         * 新增app key操作
         */
        APP_KEY_ADD(10, "新增app_key"),
        /**
         * 删除app key操作
         */
        APP_KEY_DELETE(11, "删除app_key");

        @EnumValue
        private final Integer code;

        @JsonValue
        private final String description;

        OperationType(Integer code, String description) {
            this.code = code;
            this.description = description;
        }
    }
}
