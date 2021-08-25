package com.gugu.upload.config.filter;

import com.gugu.upload.common.Result;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.JsonUtil;
import com.gugu.upload.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Auth filter.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Request path : {}, method : {}", ((HttpServletRequest)servletRequest).getRequestURI(), ((HttpServletRequest) servletRequest).getMethod());
        if (isSecurity(servletRequest)){
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

    private void refuse(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(JsonUtil.obj2JsonStr(Result.fastFail("Please login before operation")));
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

    private void pass(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
