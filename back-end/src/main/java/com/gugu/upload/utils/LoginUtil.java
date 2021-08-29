package com.gugu.upload.utils;

import com.gugu.upload.common.entity.Account;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Login util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class LoginUtil {
    private LoginUtil(){}

    /**
     * Is logged boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isLogged(HttpServletRequest request){
        return CacheUtil.isThere(SessionUtil.getKeyBySessionId(request));
    }

    /**
     * Get current account account.
     *
     * @param request the request
     * @return the account
     */
    public static Account getCurrentAccount(HttpServletRequest request){
        return CacheUtil.get(SessionUtil.getKeyBySessionId(request), Account.class);
    }
}
