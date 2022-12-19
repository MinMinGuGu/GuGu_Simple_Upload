package com.gugu.upload.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * The type Thread pool util.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/19
 * @since 1.8
 */
@Slf4j
public class ThreadPoolUtil {
    private ThreadPoolUtil() {
    }

    /**
     * Close thread pool.
     *
     * @param executorService the executor service
     */
    public static void closeThreadPool(ExecutorService executorService) {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.yield();
        }
    }
}
