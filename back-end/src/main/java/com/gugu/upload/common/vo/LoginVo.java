package com.gugu.upload.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * The type Login vo.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Data
@ApiModel(value = "LoginVo", description = "登录信息")
public class LoginVo {
    private String username;
    private String password;
    private String rememberMe;
}
