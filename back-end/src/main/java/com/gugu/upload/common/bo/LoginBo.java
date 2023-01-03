package com.gugu.upload.common.bo;

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
public class LoginBo {
    private String username;
    private String password;
    private String rememberMe;
}
