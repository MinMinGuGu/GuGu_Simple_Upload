package com.gugu.upload.event;

import com.gugu.upload.task.FileTask;
import com.gugu.upload.task.VisitTask;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * The type App closer event.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/25
 * @since 1.8
 */
@Slf4j
@Component
public class AppCloserEvent implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private VisitTask visitTask;

    @Resource
    private FileTask fileTask;

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent event) {
        saveVisit();
        clearFile();
    }

    private void clearFile() {
        log.info("Files will be cleaned up before exiting the program...");
        try {
            fileTask.cleanFile();
        } catch (IOException e) {
            log.error("An exception occurred while cleaning up the file", e);
        }
    }

    private void saveVisit(){
        log.info("Write Visit to the database before the application is closed...");
        visitTask.saveVisit();
    }
}
