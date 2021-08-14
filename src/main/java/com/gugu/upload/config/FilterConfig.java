package com.gugu.upload.config;

import com.gugu.upload.config.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Filter config.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Configuration
public class FilterConfig {

    /**
     * Auth filter filter registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        authFilterFilterRegistrationBean.setFilter(new AuthFilter());
        authFilterFilterRegistrationBean.addUrlPatterns("/*");
        return authFilterFilterRegistrationBean;
    }
}
