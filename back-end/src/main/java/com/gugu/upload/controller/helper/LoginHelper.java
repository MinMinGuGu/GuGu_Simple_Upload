package com.gugu.upload.controller.helper;

import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.LoginVo;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.MD5Util;
import com.gugu.upload.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Login util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class LoginHelper {
    private LoginHelper(){}

    /**
     * Logout boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean logout(HttpServletRequest request){
        if (isLogged(request)){
            CacheUtil.remove(SessionUtil.getKeyBySessionId(request));
            return true;
        }
        return false;
    }

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

    /**
     * Check cache boolean.
     *
     * @param httpServletRequest the http servlet request
     * @return the boolean
     */
    public static boolean checkCache(HttpServletRequest httpServletRequest) {
        return CacheUtil.isThere(SessionUtil.getKeyBySessionId(httpServletRequest));
    }

    /**
     * Save by session.
     *
     * @param loginVo            the login vo
     * @param account            the account
     * @param httpServletRequest the http servlet request
     */
    public static void saveBySession(LoginVo loginVo, Account account, HttpServletRequest httpServletRequest){
        CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(account);
        if (Boolean.TRUE.toString().equalsIgnoreCase(loginVo.getRememberMe())){
            cacheObject.setTime(24, CacheUtil.CacheObject.TimeUnit.HOUR);
        }
        CacheUtil.pull(SessionUtil.getKeyBySessionId(httpServletRequest), cacheObject);
    }

    /**
     * Find account.
     *
     * @param loginVo        the login vo
     * @param accountService the account service
     * @return the account
     */
    public static Account findAccount(LoginVo loginVo, IAccountService accountService){
        return accountService
                .query()
                .eq("user_name", loginVo.getUsername())
                .eq("user_password", MD5Util.encrypt(loginVo.getPassword()))
                .one();
    }

    /**
     * Check dto boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public static boolean checkDto(Account account){
        return account == null || account.getUserName() == null || account.getUserPassword() == null;
    }

    /**
     * Check vo boolean.
     *
     * @param loginVo the login vo
     * @return the boolean
     */
    public static boolean checkVo(LoginVo loginVo) {
        return loginVo.getUsername() == null || loginVo.getPassword() == null;
    }
}
