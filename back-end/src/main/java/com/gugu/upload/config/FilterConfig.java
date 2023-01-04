package com.gugu.upload.config;

import com.gugu.upload.config.filter.AppKeyAuthFilter;
import com.gugu.upload.config.filter.AuthFilter;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IAppKeyMapper;
import com.gugu.upload.service.ILoginService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * The type Filter config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Configuration
public class FilterConfig {

    @Resource
    private IAccountMapper accountMapper;

    @Resource
    private IAppKeyMapper appKeyMapper;

    @Resource
    private ILoginService loginService;

    /**
     * Auth filter filter registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean() {
        FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        authFilterFilterRegistrationBean.setFilter(new AuthFilter());
        authFilterFilterRegistrationBean.setUrlPatterns(AuthFilter.URL_PATTERN_LIST);
        authFilterFilterRegistrationBean.setOrder(100);
        return authFilterFilterRegistrationBean;
    }

    /**
     * App key auth filter bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<AppKeyAuthFilter> appKeyAuthFilterBean() {
        FilterRegistrationBean<AppKeyAuthFilter> appKeyAuthFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        appKeyAuthFilterFilterRegistrationBean.setFilter(new AppKeyAuthFilter(accountMapper, appKeyMapper, loginService));
        appKeyAuthFilterFilterRegistrationBean.setUrlPatterns(AppKeyAuthFilter.URL_PATTERN_LIST);
        appKeyAuthFilterFilterRegistrationBean.setOrder(99);
        return appKeyAuthFilterFilterRegistrationBean;
    }
}
