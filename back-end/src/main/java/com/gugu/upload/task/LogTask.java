package com.gugu.upload.task;

import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Visit task.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Slf4j
@Component
public class LogTask implements DisposableBean {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("LogTask_Thread");
        return thread;
    });

    @Resource
    private IOperationLogService iOperationLogService;

    /**
     * Save visit.
     */
    @Scheduled(cron = "0 0/3 * * * ? ")
    public void saveVisit() {
        executorService.execute(() -> {
            List<OperationLog> operationLogs = CacheUtil.get(OperationLog.class);
            operationLogs.forEach(iOperationLogService::save);
        });
    }

    @Override
    public void destroy() {
        ThreadPoolUtil.closeThreadPool(executorService);
    }
}
