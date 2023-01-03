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
     * Check bo boolean.
     *
     * @param loginBo the login bo
     * @return the boolean
     */
    boolean checkBo(LoginBo loginBo);

    /**
     * Check boolean.
     *
     * @param account the account
     * @return the boolean
     */
    boolean check(Account account);

    /**
     * Check cache boolean.
     *
     * @param httpServletRequest the http servlet request
     * @return the boolean
     */
    boolean checkCache(HttpServletRequest httpServletRequest);

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
}
