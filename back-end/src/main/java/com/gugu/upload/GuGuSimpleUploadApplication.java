package com.gugu.upload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Gu gu simple upload application.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@SpringBootApplication
@MapperScan("com.gugu.upload.mapper")
public class GuGuSimpleUploadApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GuGuSimpleUploadApplication.class, args);
    }

}
