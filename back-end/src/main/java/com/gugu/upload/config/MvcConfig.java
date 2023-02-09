package com.gugu.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type mvc config.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/25
 * @since 1.8
 */
@Configuration
public class MvcConfig {

    /**
     * Cors mvc config cors mvc config.
     *
     * @return the cors mvc config
     */
    @Bean
    public GuGuMvcConfig corsMvcConfig() {
        return new GuGuMvcConfig();
    }


    private static class GuGuMvcConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods(
                            HttpMethod.GET.name(),
                            HttpMethod.POST.name(),
                            HttpMethod.DELETE.name(),
                            HttpMethod.PUT.name()
                    );
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations("web/", "classpath:web/")
                    .addResourceLocations("resources/", "static/", "public/", "META-INF/resources/")
                    .addResourceLocations("classpath:resources/", "classpath:static/", "classpath:public/", "classpath:META-INF/resources/");
        }
    }
}
