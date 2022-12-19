package com.gugu.upload.task;

import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.utils.StatusUtil;
import com.gugu.upload.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * The type File task.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
@Component
public class FileTask implements DisposableBean {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("FileTask_Thread");
        return thread;
    });

    @Resource
    private IFileService fileService;

    @Resource
    private ApplicationConfig applicationConfig;

    /**
     * Check document validity.
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void checkDocumentValidity() {
        executorService.execute(() -> fileService.list().forEach(f -> {
            Path path = Paths.get(f.getFilePath());
            if (Files.notExists(path)) {
                log.info("That a file record in the database does not exist on the disk. File info : {}", f);
                flagFileInvalid(f);
            }
        }));
    }

    private void flagFileInvalid(FileInfo fileInfo) {
        fileInfo.setStatus(StatusUtil.Status.FAIL);
        fileService.updateById(fileInfo);
    }

    /**
     * Clean up records.
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void cleanUpRecords() {
        executorService.execute(() -> fileService.list().forEach(f -> {
            if (StatusUtil.Status.FAIL == f.getStatusDescription()) {
                log.info("The updated value is logic delete : {}", f);
                fileService.removeById(f.getId());
            }
        }));
    }

    /**
     * Clean file.
     *
     * @throws IOException the io exception
     */
    @Scheduled(cron = "0 0/20 * * * ? ")
    public void cleanFile() throws IOException {
        executorService.execute(() -> {
            Map<String, String> dataMap = fileService.list().stream().collect(Collectors.toMap(FileInfo::getFilePath, FileInfo::getFilePath));
            Path path = Paths.get(applicationConfig.getTmpDir());
            if (Files.exists(path)) {
                try {
                    Files.walkFileTree(path, new CleanUpIrrelevantFiles(dataMap, applicationConfig));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    log.error("com.gugu.upload.task.FileTask.cleanFile error", ioException);
                }
            }
        });
    }

    @Override
    public void destroy() {
        ThreadPoolUtil.closeThreadPool(executorService);
    }

    private static class CleanUpIrrelevantFiles extends SimpleFileVisitor<Path> {

        private final ApplicationConfig applicationConfig;

        private final Map<String, String> dataMap;

        /**
         * Instantiates a new Clean up irrelevant files.
         *
         * @param dataMap the data map
         */
        public CleanUpIrrelevantFiles(Map<String, String> dataMap, ApplicationConfig applicationConfig) {
            this.dataMap = dataMap;
            this.applicationConfig = applicationConfig;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String filePath = dataMap.get(file.toString());
            if (StringUtils.isEmpty(filePath)) {
                log.info("About to delete file : {}", file);
                Files.delete(file);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            if (checkPath(dir)) {
                log.info("Irrelevant files will be deleted : {}", dir);
                try {
                    Files.delete(dir);
                } catch (IOException e) {
                    log.info("Failed to delete irrelevant files");
                }
            }
            return FileVisitResult.CONTINUE;
        }

        private boolean checkPath(Path path) {
            if (StringUtils.isEmpty(path)) {
                return false;
            }
            try {
                if (Files.isSameFile(path, Paths.get(applicationConfig.getTmpDir()))) {
                    return false;
                }
            } catch (IOException e) {
                log.error("Failed to check folder before deleting", e);
                return false;
            }
            return true;
        }
    }
}
