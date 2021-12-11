package com.gugu.upload.config;

import com.gugu.upload.exception.InitException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * The type Data source config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Setter
@Configuration
@ConfigurationProperties("spring.datasource")
@MapperScan("com.gugu.upload.mapper")
public class DataSourceConfig {

    private static final String URL_SPLIT = "?";

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    /**
     * Hikari data source data source.
     *
     * @return the data source
     */
    @Bean
    @Primary
    public DataSource hikariDataSource(){
        String jdbcUrl = url;
        String jdbcDataBaseUrl = jdbcUrl.substring(0, jdbcUrl.lastIndexOf("?"));
        String dataBaseName = jdbcDataBaseUrl.substring(jdbcDataBaseUrl.lastIndexOf("/") + 1);
        String baseJdbcUrl = jdbcDataBaseUrl.substring(0, jdbcDataBaseUrl.lastIndexOf("/"));
        if (jdbcUrl.contains(URL_SPLIT)) {
            baseJdbcUrl += jdbcUrl.substring(jdbcUrl.lastIndexOf("?"));
        }
        try {
            Class.forName(driverClassName);
            Connection connection = DriverManager.getConnection(baseJdbcUrl, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("create database if not exists `%s` default character set utf8 COLLATE utf8_general_ci;", dataBaseName));
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InitException(e.getMessage(), e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }
}
