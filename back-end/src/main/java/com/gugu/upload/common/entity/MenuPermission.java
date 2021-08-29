package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * The type Menu permission.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/28
 * @since 1.8
 */
@Data
@TableName("menuPermission")
public class MenuPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer menuId;
    private Integer permissionId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
