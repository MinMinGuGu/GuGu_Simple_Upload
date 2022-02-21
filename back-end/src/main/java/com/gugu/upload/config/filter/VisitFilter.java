package com.gugu.upload.config.filter;

import com.gugu.upload.common.entity.Visit;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * The type Record filter.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Slf4j
public class VisitFilter implements Filter {

    private static final String RECORD_CACHE_PREFIX = "record_cache_key_";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Visit visit = getVisit(servletRequest);
        log.info("visit : {}", visit);
        CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(visit, 10, CacheUtil.CacheObject.TimeUnit.MINUTE);
        CacheUtil.pull(generateCacheName(), cacheObject);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Visit getVisit(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ipAddress = IpUtil.getIpAddress(request);
        String path = request.getRequestURI();
        Visit visit = new Visit();
        visit.setIpAddress(ipAddress);
        visit.setPath(path);
        visit.setMethod(request.getMethod());
        return visit;
    }

    private String generateCacheName() {
        return RECORD_CACHE_PREFIX + UUID.randomUUID().toString().replace("-", "");
    }
}
