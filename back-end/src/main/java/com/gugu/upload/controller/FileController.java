package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.query.FileInfoQueryRequest;
import com.gugu.upload.common.vo.FileInfoVo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.exception.UnknownException;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.utils.FileUtil;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.StatusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Upload controller.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Api("文件API")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private IFileService fileService;

    @Resource
    private ApplicationConfig applicationConfig;

    /**
     * File download.
     *
     * @param id       the id
     * @param response the response
     */
    @GetMapping("/{id}")
    @ApiOperation("文件下载")
    @ApiImplicitParam(paramType = "path", name = "id", value = "文件id", required = true)
    public void fileDownLoad(@PathVariable Integer id, HttpServletResponse response) {
        log.info("ID of the file requested to download : {}", id);
        fileService.downloadFileById(id, response);
    }

    /**
     * File delete result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/{id}")
    @ApiOperation("文件删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "文件id", required = true)
    public Result<?> fileDelete(@PathVariable Integer id) {
        log.info("The ID of the file requested to be deleted : {}", id);
        fileService.removeById(id);
        return Result.fastSuccess();
    }

    /**
     * Find list result.
     *
     * @param fileInfoQueryRequest the file info query request
     * @return the result
     */
    @GetMapping("/query")
    @ApiOperation("获取管理中的文件列表")
    @ApiImplicitParam(paramType = "body", name = "fileInfoQueryRequest", value = "文件查询参数")
    public Result<List<FileInfoVo>> findList(@RequestBody(required = false) FileInfoQueryRequest fileInfoQueryRequest) {
        if (fileInfoQueryRequest == null){
            fileInfoQueryRequest = new FileInfoQueryRequest();
        }
        List<FileInfoVo> fileInfos = fileService.getFileInfoList(fileInfoQueryRequest);
        return new Result.Builder<List<FileInfoVo>>().success(fileInfos).build();
    }

    /**
     * Upload result.
     *
     * @param multipartFiles the multipart files
     * @param request        the request
     * @return the result
     */
    @ApiOperation("上传文件")
    @ApiImplicitParam(paramType = "body", name = "multipartFiles", value = "文件", required = true)
    @PostMapping
    public Result<List<FileInfoVo>> upload(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest request) {
        List<FileInfoVo> fileInfoVos = new LinkedList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileInfoBo fileInfoBo = initBo(multipartFile, request);
            log.info("The received file information is : {}", fileInfoBo);
            try {
                log.info("Save path : {}", fileInfoBo.getFilePath());
                multipartFile.transferTo(Paths.get(fileInfoBo.getFilePath()));
                fileInfoBo.setStatus(StatusUtil.Status.SUCCESS);
                log.info("File successfully written to disk");
            } catch (IOException e) {
                fileInfoBo.setStatus(StatusUtil.Status.FAIL);
                log.error("Failed to write file to disk", e);
            }
            FileInfoVo fileInfoVo = fileService.uploadSave(fileInfoBo);
            fileInfoVos.add(fileInfoVo);
        }
        return new Result.Builder<List<FileInfoVo>>().success(fileInfoVos).build();
    }

    private FileInfoBo initBo(MultipartFile multipartFile, HttpServletRequest request) {
        FileInfoBo fileInfoBo = new FileInfoBo();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new UnknownException("接收过来的文件名异常");
            }
            String fileSuffix = FileUtil.getFileSuffix(originalFilename);
            fileInfoBo
                    .setFileHash(FileUtil.getFileHashCode(multipartFile.getInputStream()))
                    .setFilePath(getSavePath(FileUtil.getUniqueFileName() + fileSuffix).toString())
                    .setFileOriginal(originalFilename)
                    .setUploader(this.getUploader(request))
                    .setFileSize(String.valueOf(multipartFile.getSize()));
        } catch (IOException e) {
            log.error("Failed to initialize file Bo object", e);
            throw new UnknownException(e.getMessage(), e);
        }
        return fileInfoBo;
    }

    private String getUploader(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

    private Path getSavePath(String filePath) {
        return getTmpDir().resolve(filePath);
    }

    private Path getTmpDir() {
        Path path = Paths.get(applicationConfig.getTmpDir());
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("Failed to create folder", e);
                throw new UnknownException("创建文件夹失败", e);
            }
        }
        return path;
    }
}
