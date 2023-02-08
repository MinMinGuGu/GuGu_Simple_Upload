package com.gugu.upload.config.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * The type Path filter.
 *
 * @author minmin
 * @version 1.0
 * @date 2023 /02/08
 * @since 1.8
 */
public class PathFilter extends BaseFilter implements Filter {

    private static final String REWRITE_FLAG = "/api";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestUri = httpServletRequest.getRequestURI();
        if (requestUri.startsWith(REWRITE_FLAG)) {
            String newUri = requestUri.replace(REWRITE_FLAG, "");
            httpServletRequest.getRequestDispatcher(newUri).forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public List<String> getUrlPatternList() {
        return Collections.singletonList("/api/*");
    }
}
