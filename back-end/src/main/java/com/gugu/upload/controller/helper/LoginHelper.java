package com.gugu.upload.controller.helper;

import com.gugu.upload.common.entity.Account;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.SessionUtil;
import com.gugu.upload.utils.TomcatUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public static boolean logout(HttpServletRequest request) {
        if (isLogged(request)) {
            CacheUtil.remove(SessionUtil.getKeyBySessionId(request));
            return true;
        }
        return false;
    }

    /**
     * Logout boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public static boolean logout(Account account) {
        String key = getAccountKeyByCache(account);
        if (StringUtils.hasText(key)) {
            CacheUtil.remove(key);
            return true;
        }
        return false;
    }

    private static String getAccountKeyByCache(Account account) {
        List<Map<String, Account>> maps = CacheUtil.get(Account.class);
        for (Map<String, Account> map : maps) {
            for (Map.Entry<String, Account> accountEntry : map.entrySet()) {
                if (accountEntry.getValue().getUserName().equals(account.getUserName())) {
                    return accountEntry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Is logged boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isLogged(HttpServletRequest request) {
        return CacheUtil.isThere(SessionUtil.getKeyBySessionId(request));
    }

    /**
     * Get current account account.
     *
     * @return the account
     */
    public static Account getCurrentAccount() {
        return getCurrentAccount(TomcatUtil.getHttpRequestFroCurrThread());
    }

    /**
     * Get current account account.
     *
     * @param request the request
     * @return the account
     */
    public static Account getCurrentAccount(HttpServletRequest request) {
        return CacheUtil.get(SessionUtil.getKeyBySessionId(request), Account.class);
    }
}
