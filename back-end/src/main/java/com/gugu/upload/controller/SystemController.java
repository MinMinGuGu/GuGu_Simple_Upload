package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.system.info.FileUploadVo;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * The type System controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/09
 * @since 1.8
 */
@Api("系统相关")
@Slf4j
@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    private IFileService fileService;

    @Resource
    private IAccountService accountService;

    @GetMapping("/info/fileUpload")
    @ApiOperation("获取用户在系统上传的文件概览信息")
    public Result<?> getFileUploadInfo(HttpServletRequest request){
        Account currentAccount = LoginHelper.getCurrentAccount(request);
        int systemFileNum = fileService.getAllFileCount();
        int userFileUploadNum = accountService.getUserAllFileCount(currentAccount);
        FileUploadVo fileUploadVo = new FileUploadVo(systemFileNum, userFileUploadNum);
        log.info("currentAccount: {}, File information uploaded by the user in the system: {}", currentAccount, fileUploadVo);
        return Result.fastSuccess(fileUploadVo);
    }
}