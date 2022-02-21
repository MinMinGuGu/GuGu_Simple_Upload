package com.gugu.upload.common.vo.system.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * The type Account vo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/16
 * @since 1.8
 */
@Data
public class AccountVo {
    private Integer id;
    private String userName;
    private Integer roleId;
    private String roleName;
    private Boolean systemDefault;
    private Boolean enable;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
