package com.gugu.upload.common.bo;

import lombok.Data;

/**
 * The type Account dto.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/23
 * @since 1.8
 */
@Data
public class AccountBo {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private Boolean enable;
}
