package com.gugu.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * The type Thread pool config.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class ThreadPoolConfig {
    /**
     * Thread pool task scheduler thread pool task scheduler.
     *
     * @return the thread pool task scheduler
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("File Task Pool");
        return threadPoolTaskScheduler;
    }
}
