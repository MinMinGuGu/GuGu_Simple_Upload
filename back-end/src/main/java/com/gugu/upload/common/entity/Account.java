package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * The type Account entity.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Data
@TableName("account")
public class Account {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String userPassword;
}
