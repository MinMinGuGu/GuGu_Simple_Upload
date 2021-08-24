package com.gugu.upload.config;

import com.gugu.upload.common.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * The type Thread pool config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
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
        threadPoolTaskScheduler.setPoolSize(Constant.CPU_INTENSIVE);
        threadPoolTaskScheduler.setThreadNamePrefix("Task Pool");
        return threadPoolTaskScheduler;
    }
}
