package com.gugu.upload.task;

import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.ThreadPoolUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Cache task.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/25
 * @since 1.8
 */
@Component
public class CacheTask implements DisposableBean {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("CacheTask_Thread");
        return thread;
    });

    /**
     * Clean cache.
     */
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void cleanCache() {
        executorService.execute(CacheUtil::clearUp);
    }

    @Override
    public void destroy() {
        ThreadPoolUtil.closeThreadPool(executorService);
    }
}
