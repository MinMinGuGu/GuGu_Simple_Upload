package com.gugu.upload.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gugu.upload.common.converter.DateConverter;
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
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("ip地址")
    private String ipAddress;
    @ExcelProperty("操作名")
    private String operationName;
    @ExcelProperty("用户名")
    private String userName;
    @ExcelProperty("内容")
    private String context;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(value = "操作日期", converter = DateConverter.class)
    private LocalDateTime createTime;

    /**
     * The enum Operation type.
     *
     * @author minmin
     * @version 1.0
     * @since 1.8
     */
    @Getter
    public enum OperationName {
        /**
         * 未知操作
         */
        UNKNOWN("未知操作"),
        /**
         * 用户登录操作
         */
        LOGIN("用户登录"),
        /**
         * 文件上传操作
         */
        FILE_UPLOAD("文件上传"),
        /**
         * 文件删除操作
         */
        FILE_DELETE("文件删除"),
        /**
         * 新增用户操作
         */
        USER_ADD("新增用户"),
        /**
         * 修改用户操作
         */
        USER_UPDATE("修改用户"),
        /**
         * 删除用户操作
         */
        USER_DELETE("删除用户"),
        /**
         * 新增角色操作
         */
        ROLE_ADD("新增角色"),
        /**
         * 修改角色操作
         */
        ROLE_UPDATE("修改角色"),
        /**
         * 删除角色操作
         */
        ROLE_DELETE("删除角色"),
        /**
         * 新增app key操作
         */
        APP_KEY_ADD("新增AppKey"),
        /**
         * 删除app key操作
         */
        APP_KEY_DELETE("删除AppKey");

        private final String description;

        OperationName(String description) {
            this.description = description;
        }
    }
}
