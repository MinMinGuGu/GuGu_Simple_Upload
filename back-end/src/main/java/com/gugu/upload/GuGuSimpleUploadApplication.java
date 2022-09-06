package com.gugu.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The type GuGu simple upload application.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuGuSimpleUploadApplication {

    public static ConfigurableApplicationContext applicationContext;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(GuGuSimpleUploadApplication.class, args);
    }

}
