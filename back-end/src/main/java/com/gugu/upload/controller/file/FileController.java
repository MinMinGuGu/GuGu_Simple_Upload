package com.gugu.upload.controller.file;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.dto.UpdateFileDto;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.query.FileInfoQueryRequest;
import com.gugu.upload.common.vo.FileInfoVo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.controller.helper.FileHelper;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.StatusUtil;
import com.gugu.upload.utils.TransformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Resource
    private IOperationLogService operationLogService;

    /**
     * Update file desc result.
     *
     * @param id       the id
     * @param fileDesc the file desc
     * @return the result
     */
    @PutMapping("/{id}")
    @ApiOperation("更新文件描述")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "文件的id", required = true),
            @ApiImplicitParam(paramType = "query", name = "fileDesc", value = "文件的描述", required = true)
    })
    public Result<String> updateFileDesc(@PathVariable("id") Integer id, @RequestParam String fileDesc){
        UpdateFileDto updateFileDto = new UpdateFileDto(id, fileDesc);
        log.info("File ID to be updated: {}, desc: {}", id, fileDesc);
        fileService.updateById(TransformUtil.transform(updateFileDto, FileInfo.class));
        return Result.fastSuccess();
    }

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
        FileInfo fileInfo = fileService.deleteFileReturnEntity(id);
        operationLogService.recordLog(OperationLog.OperationType.FILE_DELETE, fileInfo.getFilePath());
        return Result.fastSuccess();
    }

    /**
     * Find list result.
     *
     * @param fileInfoQueryRequest the file info query request
     * @return the result
     */
    @GetMapping
    @ApiOperation("获取管理中的文件列表")
    @ApiImplicitParam(paramType = "query", name = "fileInfoQueryRequest", value = "文件查询参数")
    public Result<List<FileInfoVo>> findList(@ModelAttribute FileInfoQueryRequest fileInfoQueryRequest) {
        if (fileInfoQueryRequest == null) {
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
            FileInfoBo fileInfoBo = FileHelper.initBo(multipartFile, request, applicationConfig);
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
            operationLogService.recordLog(OperationLog.OperationType.FILE_UPLOAD, fileInfoBo.getFilePath());
            fileInfoVos.add(fileInfoVo);
        }
        return new Result.Builder<List<FileInfoVo>>().success(fileInfoVos).build();
    }
}
