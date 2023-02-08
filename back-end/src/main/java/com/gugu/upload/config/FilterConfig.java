package com.gugu.upload.config;

import com.gugu.upload.config.filter.AppKeyAuthFilter;
import com.gugu.upload.config.filter.AuthFilter;
import com.gugu.upload.config.filter.PathFilter;
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
        AuthFilter filter = new AuthFilter();
        authFilterFilterRegistrationBean.setFilter(filter);
        authFilterFilterRegistrationBean.setUrlPatterns(filter.getUrlPatternList());
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
        AppKeyAuthFilter filter = new AppKeyAuthFilter(accountMapper, appKeyMapper, loginService);
        appKeyAuthFilterFilterRegistrationBean.setFilter(filter);
        appKeyAuthFilterFilterRegistrationBean.setUrlPatterns(filter.getUrlPatternList());
        appKeyAuthFilterFilterRegistrationBean.setOrder(99);
        return appKeyAuthFilterFilterRegistrationBean;
    }

    /**
     * Path filter bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<PathFilter> pathFilterBean() {
        FilterRegistrationBean<PathFilter> pathFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        PathFilter filter = new PathFilter();
        pathFilterFilterRegistrationBean.setFilter(filter);
        pathFilterFilterRegistrationBean.setUrlPatterns(filter.getUrlPatternList());
        pathFilterFilterRegistrationBean.setOrder(0);
        return pathFilterFilterRegistrationBean;
    }
}
