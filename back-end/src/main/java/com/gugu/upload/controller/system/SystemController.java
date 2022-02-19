package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.service.IRoleService;
import com.gugu.upload.utils.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @Resource
    private IRoleService roleService;

    @GetMapping("/account/fileUpload/info")
    @ApiOperation("获取用户在系统上传的文件数量")
    public Result<?> getAccountFileUploadInfo(HttpServletRequest request) {
        Account currentAccount = LoginHelper.getCurrentAccount(request);
        int userFileUploadCount = accountService.getUserAllFileCount(currentAccount);
        return Result.fastSuccess(MapUtil.toMap("userFileUploadCount", userFileUploadCount));
    }

    @ApiOperation("获取系统上所有的上传文件数量")
    @GetMapping("/fileUpload/info")
    public Result<?> getSystemFileUploadInfo() {
        int systemFileCount = fileService.getAllFileCount();
        return Result.fastSuccess(MapUtil.toMap("systemFileCount", systemFileCount));
    }

    @ApiOperation("获取系统上近七天的文件上传信息")
    @GetMapping("/fileUpload/week/info")
    public Result<?> getWeekFileUploadInfo() {
        List<Map<String, Object>> data = fileService.getWeekFileUploadData();
        return Result.fastSuccess(data);
    }

    @GetMapping("/roles")
    @ApiOperation("获取系统所有角色")
    public Result<?> getSystemRoles() {
        List<Role> roleList = roleService.list();
        return Result.fastSuccess(roleList);
    }
}