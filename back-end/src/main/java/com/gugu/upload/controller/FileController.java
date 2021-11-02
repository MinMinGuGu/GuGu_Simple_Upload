package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.common.vo.FileInfoVo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.exception.UnknownException;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.utils.FileUtil;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.StatusUtil;
import com.gugu.upload.utils.TransformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Api("文件相关")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private IFileService fileService;

    @Resource
    private ApplicationConfig applicationConfig;

    @DeleteMapping("/{id}")
    @ApiOperation("文件删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "文件id", required = true)
    public Result<?> fileDelete(@PathVariable Integer id){
        fileService.removeById(id);
        return Result.fastSuccess();
    }

    /**
     * Find list result.
     *
     * @return the result
     */
    @GetMapping
    @ApiOperation("获取管理中的文件列表")
    public Result<List<FileInfo>> findList(){
        List<FileInfo> fileInfos = fileService.list();
        return new Result.Builder<List<FileInfo>>().success(fileInfos).build();
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
    public Result<List<FileInfoVo>> upload(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest request){
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
                e.printStackTrace();
                fileInfoBo.setStatus(StatusUtil.Status.FAIL);
                log.info("Failed to write file to disk");
            }
            FileInfo fileInfo = TransformUtil.transform(fileInfoBo, FileInfo.class);
            fileService.save(fileInfo);
            log.info("Complete the database insert. file info : {}", fileInfo);
            fileInfoVos.add(TransformUtil.transform(fileInfoBo, FileInfoVo.class));
        }
        return new Result.Builder<List<FileInfoVo>>().success(fileInfoVos).build();
    }

    private FileInfoBo initBo(MultipartFile multipartFile, HttpServletRequest request){
        FileInfoBo fileInfoBo = new FileInfoBo();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null){
                throw new UnknownException("接收过来的文件名异常");
            }
            String fileSuffix = FileUtil.getFileSuffix(originalFilename);
            fileInfoBo
                    .setFileHash(FileUtil.getFileHashCode(multipartFile.getInputStream()))
                    .setFilePath(getSavePath(FileUtil.getUniqueFileName() + fileSuffix).toString())
                    .setFileOriginal(originalFilename)
                    .setUploader(this.getUploader(request))
                    .setFileSize(conversionMb(multipartFile.getSize()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnknownException(e.getMessage(), e);
        }
        return fileInfoBo;
    }

    private String getUploader(HttpServletRequest request){
        return IpUtil.getIpAddress(request);
    }

    private Path getSavePath(String filePath){
        return getTmpDir().resolve(filePath);
    }

    private Path getTmpDir(){
        Path path = Paths.get(applicationConfig.getTmpDir());
        if (Files.notExists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new UnknownException("创建文件夹失败", e);
            }
        }
        return path;
    }

    private String conversionMb(long size){
        return String.format("%sMB", (float)size / 1024 / 1024);
    }
}
