package com.gugu.upload.service;

import com.gugu.upload.common.bo.LoginBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Login service.
 *
 * @author minmin
 * @version 1.0
 * @date 2023 /01/04
 * @since 1.8
 */
public interface ILoginService {
    /**
     * Find account account.
     *
     * @param loginBo the login bo
     * @return the account
     */
    Account findAccount(LoginBo loginBo);

    /**
     * Save by session login vo.
     *
     * @param loginBo            the login bo
     * @param account            the account
     * @param httpServletRequest the http servlet request
     * @return the login vo
     */
    LoginVo saveBySession(LoginBo loginBo, Account account, HttpServletRequest httpServletRequest);

    /**
     * Gets current account.
     *
     * @param request the request
     * @return the current account
     */
    Account getCurrentAccount(HttpServletRequest request);

    /**
     * Account 2 login vo login vo.
     *
     * @param account the account
     * @return the login vo
     */
    LoginVo account2LoginVo(Account account);
}
