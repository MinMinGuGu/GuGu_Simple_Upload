package com.gugu.upload.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Mybatis plus config.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /02/23
 * @since 1.8
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * Inner interceptor mybatis plus interceptor.
     *
     * @return the mybatis plus interceptor
     */
    @Bean
    public MybatisPlusInterceptor innerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
