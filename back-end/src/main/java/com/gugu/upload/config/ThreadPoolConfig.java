package com.gugu.upload.config;

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
     * The constant CPU_INTENSIVE.
     */
    public static final int CPU_INTENSIVE = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * The constant IO_INTENSIVE.
     */
    public static final int IO_INTENSIVE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * Thread pool task scheduler thread pool task scheduler.
     *
     * @return the thread pool task scheduler
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(CPU_INTENSIVE);
        threadPoolTaskScheduler.setThreadNamePrefix("Task Pool");
        return threadPoolTaskScheduler;
    }
}
