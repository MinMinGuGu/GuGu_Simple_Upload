package com.gugu.upload.utils;

import com.gugu.upload.exception.FunctionException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The type Spring context util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private SpringContextUtil(){}

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * Get application context application context.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext(){
        if (applicationContext == null){
            throw new FunctionException("无法载入ApplicationContext");
        }
        return applicationContext;
    }
}
