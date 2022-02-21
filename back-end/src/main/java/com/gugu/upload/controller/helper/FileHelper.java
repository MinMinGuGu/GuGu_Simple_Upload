package com.gugu.upload.controller.helper;

import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.exception.UnknownException;
import com.gugu.upload.utils.FileUtil;
import com.gugu.upload.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type File helper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /12/19
 * @since 1.8
 */
@Slf4j
public class FileHelper {

    private FileHelper(){}

    /**
     * Init bo file info bo.
     *
     * @param multipartFile     the multipart file
     * @param request           the request
     * @param applicationConfig the application config
     * @return the file info bo
     */
    public static FileInfoBo initBo(MultipartFile multipartFile, HttpServletRequest request, ApplicationConfig applicationConfig) {
        FileInfoBo fileInfoBo = new FileInfoBo();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new UnknownException("Received file name exception");
            }
            String fileSuffix = FileUtil.getFileSuffix(originalFilename);
            Account currentAccount = LoginHelper.getCurrentAccount(request);
            fileInfoBo
                    .setFileHash(FileUtil.getFileHashCode(multipartFile.getInputStream()))
                    .setFilePath(getSavePath(FileUtil.getUniqueFileName() + fileSuffix, applicationConfig).toString())
                    .setFileOriginal(originalFilename)
                    .setUploader(getUploader(request))
                    .setFileSize(String.valueOf(multipartFile.getSize()))
                    .setAccountId(currentAccount.getId());
        } catch (IOException e) {
            log.error("Failed to initialize file Bo object", e);
            throw new UnknownException(e.getMessage(), e);
        }
        return fileInfoBo;
    }

    private static String getUploader(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

    private static Path getSavePath(String filePath, ApplicationConfig applicationConfig) {
        return getTmpDir(applicationConfig).resolve(filePath);
    }

    private static Path getTmpDir(ApplicationConfig applicationConfig) {
        Path path = Paths.get(applicationConfig.getTmpDir());
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("Failed to create folder", e);
                throw new UnknownException("Failed to create folder", e);
            }
        }
        return path;
    }
}
