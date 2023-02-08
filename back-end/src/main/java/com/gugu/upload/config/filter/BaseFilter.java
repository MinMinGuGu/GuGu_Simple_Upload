package com.gugu.upload.config.filter;

import com.gugu.upload.common.Result;
import com.gugu.upload.utils.JsonUtil;
import org.springframework.http.MediaType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Base filter.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
public abstract class BaseFilter {

    /**
     * The constant NOT_LOGIN_HTTP_STATUS.
     */
    protected static final Integer NOT_LOGIN_HTTP_STATUS = 401;

    /**
     * Pass.
     *
     * @param servletRequest  the servlet request
     * @param servletResponse the servlet response
     * @param filterChain     the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    public void pass(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Refuse.
     *
     * @param servletResponse the servlet response
     * @throws IOException the io exception
     */
    public void refuse(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(NOT_LOGIN_HTTP_STATUS);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(JsonUtil.obj2JsonStr(
                new Result.Builder<String>()
                        .code(NOT_LOGIN_HTTP_STATUS)
                        .message("Please authenticate before operation")
                        .build()
        ));
    }

    /**
     * 获取url匹配拦截列表
     *
     * @return 匹配拦截列表 url pattern list
     */
    public abstract List<String> getUrlPatternList();
}
