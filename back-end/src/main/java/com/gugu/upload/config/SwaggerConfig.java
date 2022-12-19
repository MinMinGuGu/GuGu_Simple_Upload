package com.gugu.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Swagger config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_VERSION = "0.0.1";

    /**
     * Login docket.
     *
     * @return the docket
     */
    @Bean
    public Docket login(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("login")
                .apiInfo(getLoginApiInfo())
                .select()
                .paths(PathSelectors.regex("/login.*"))
                .build();
    }

    private ApiInfo getLoginApiInfo() {
        return new ApiInfoBuilder()
                .title("Login Api")
                .description("登录接口")
                .version(API_VERSION)
                .build();
    }

    /**
     * File docket.
     *
     * @return the docket
     */
    @Bean
    public Docket file(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("file")
                .apiInfo(getFileApiInfo())
                .select()
                .paths(PathSelectors.regex("/file.*"))
                .build();
    }

    private ApiInfo getFileApiInfo() {
        return new ApiInfoBuilder()
                .title("File Api")
                .description("文件接口")
                .version(API_VERSION)
                .build();
    }

    /**
     * System docket.
     *
     * @return the docket
     */
    @Bean
    public Docket system() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("system")
                .apiInfo(getSystemApiInfo())
                .select()
                .paths(PathSelectors.regex("/system.*"))
                .build();
    }

    private ApiInfo getSystemApiInfo() {
        return new ApiInfoBuilder()
                .title("System Api")
                .description("系统接口")
                .version(API_VERSION)
                .build();
    }
}
