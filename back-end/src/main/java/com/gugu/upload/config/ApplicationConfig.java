package com.gugu.upload.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Application config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties("config")
public class ApplicationConfig {
    private String dir;
    private String tmpDir;
    private String logDir;
}
