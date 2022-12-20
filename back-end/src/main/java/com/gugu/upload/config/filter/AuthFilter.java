package com.gugu.upload.config.filter;

import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * The type Auth filter.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
public class AuthFilter extends BaseFilter implements Filter {

    /**
     * The constant URL_PATTERN_LIST.
     */
    public static final List<String> URL_PATTERN_LIST = Collections.singletonList("/*");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Request path : {}, method : {}", ((HttpServletRequest) servletRequest).getRequestURI(), ((HttpServletRequest) servletRequest).getMethod());
        if (isSecurity(servletRequest)) {
            log.info("pass requests...");
            pass(servletRequest, servletResponse, filterChain);
            return;
        }
        String sessionId = SessionUtil.getKeyBySessionId(servletRequest);
        log.info("About to verify login status. session id : {}", sessionId);
        if (CacheUtil.isThere(sessionId)) {
            log.info("User login verification succeeded.");
            pass(servletRequest, servletResponse, filterChain);
            return;
        }
        log.info("User login verification failed. session id : {}", sessionId);
        refuse(servletResponse);
    }

    private boolean isSecurity(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI();
        return "/login".equals(requestUri)
                || requestUri.contains("swagger")
                || requestUri.contains(".ico")
                || requestUri.contains(".js")
                || requestUri.contains(".css")
                || requestUri.contains(".woft2")
                || requestUri.contains("/csrf")
                || requestUri.contains("/api-docs")
                ;
    }
}
