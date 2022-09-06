package com.gugu.upload.utils;

import com.gugu.upload.GuGuSimpleUploadApplication;
import com.gugu.upload.exception.FunctionException;
import org.springframework.context.ApplicationContext;

/**
 * The type Spring context util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class SpringContextUtil {
    private static final ApplicationContext APPLICATION_CONTEXT;

    static {
        APPLICATION_CONTEXT = GuGuSimpleUploadApplication.applicationContext;
    }

    private SpringContextUtil() {
    }

    /**
     * Get application context application context.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext() {
        if (APPLICATION_CONTEXT == null) {
            throw new FunctionException("无法载入ApplicationContext");
        }
        return APPLICATION_CONTEXT;
    }
}
