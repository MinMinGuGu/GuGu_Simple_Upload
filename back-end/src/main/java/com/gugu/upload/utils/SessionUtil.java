package com.gugu.upload.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Session util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class SessionUtil {
    private SessionUtil(){}

    /**
     * Gets key by session.
     *
     * @param httpServletRequest the http servlet request
     * @return the key by session
     */
    public static String getKeyBySessionId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getId();
    }

    /**
     * Gets key by session.
     *
     * @param servletRequest the servlet request
     * @return the key by session
     */
    public static String getKeyBySessionId(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return getKeyBySessionId(request);
    }
}
