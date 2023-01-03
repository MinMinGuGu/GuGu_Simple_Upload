package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Permission controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /03/12
 * @since 1.8
 */
@Api("系统权限相关")
@Slf4j
@RestController
@RequestMapping("/system")
@PermissionCheck
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    /**
     * Gets system permissions.
     *
     * @return the system permissions
     */
    @GetMapping("/permissions")
    @ApiOperation("获取系统所有权限列表")
    public Result<?> getSystemPermissions() {
        List<Permission> permissionList = permissionService.list();
        return Result.fastSuccess(permissionList);
    }
}
