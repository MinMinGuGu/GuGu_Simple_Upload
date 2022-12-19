package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The type Role.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/28
 * @since 1.8
 */
@Data
@TableName("role")
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Boolean enable;
    private Boolean systemDefault;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * The enum Default role enum.
     *
     * @author minmin
     * @version 1.0
     * @since 1.8
     */
    @Getter
    public enum DefaultRoleEnum {
        /**
         * 超级管理员
         */
        SUPER_ADMIN("超级管理员"),
        /**
         * 用户
         */
        USER("用户"),
        /**
         * 普通管理员
         */
        ADMIN("普通管理员");
        private final String name;

        DefaultRoleEnum(String name) {
            this.name = name;
        }
    }
}
