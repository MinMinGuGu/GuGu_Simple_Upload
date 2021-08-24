package com.gugu.upload.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Session util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class SessionUtil {
    private SessionUtil(){}

    /**
     * Gets key by session id.
     *
     * @param httpServletRequest the http servlet request
     * @return the key by session id
     */
    public static String getKeyBySessionId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getId();
    }

    /**
     * Gets key by session id.
     *
     * @param servletRequest the servlet request
     * @return the key by session id
     */
    public static String getKeyBySessionId(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return getKeyBySessionId(request);
    }
}
