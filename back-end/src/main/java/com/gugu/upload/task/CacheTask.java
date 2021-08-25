package com.gugu.upload.task;

import com.gugu.upload.utils.CacheUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The type Cache task.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/25
 * @since 1.8
 */
@Component
public class CacheTask {

    /**
     * Clean cache.
     */
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void cleanCache(){
        CacheUtil.clearUp();
    }
}
