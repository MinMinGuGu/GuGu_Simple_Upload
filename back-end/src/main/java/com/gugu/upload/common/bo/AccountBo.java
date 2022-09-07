package com.gugu.upload.common.bo;

import com.gugu.upload.common.entity.Account;
import com.gugu.upload.utils.MD5Util;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * The type Account dto.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/23
 * @since 1.8
 */
@Data
public class AccountBo implements IBo2Entity<Account> {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private Boolean enable;

    @Override
    public Account bo2Entity() {
        Account account = new Account();
        account.setId(id);
        account.setUserName(username);
        if (StringUtils.hasText(password)) {
            account.setUserPassword(MD5Util.encrypt(password));
        }
        account.setRoleId(roleId);
        account.setEnable(enable);
        return account;
    }
}
