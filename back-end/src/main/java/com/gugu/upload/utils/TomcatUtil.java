package com.gugu.upload.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * The type Tomcat util.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
public class TomcatUtil {
    private TomcatUtil() {
    }

    /**
     * 从当前线程中获取httpRequest对象
     *
     * @return the http servlet request
     */
    public static HttpServletRequest getHttpRequestFroCurrThread() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
