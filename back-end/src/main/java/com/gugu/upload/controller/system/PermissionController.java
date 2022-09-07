package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
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
 * @author minmin
 * @date 2022/03/12
 */
@Api("系统权限相关")
@Slf4j
@RestController
@RequestMapping("/system")
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    @GetMapping("/permissions")
    @ApiOperation("获取系统所有权限列表")
    public Result<?> getSystemPermissions() {
        List<Permission> permissionList = permissionService.list();
        return Result.fastSuccess(permissionList);
    }
}
