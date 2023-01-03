package com.gugu.upload.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gugu.upload.common.Result;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.bo.RoleBo;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.entity.RolePermission;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * The type Role controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /02/22
 * @since 1.8
 */
@Slf4j
@Api("系统角色相关")
@RestController
@RequestMapping("/system")
@PermissionCheck
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IOperationLogService operationLogService;

    /**
     * Gets system roles.
     *
     * @return the system roles
     */
    @GetMapping("/roles")
    @ApiOperation("获取系统所有角色")
    public Result<?> getSystemRoles() {
        List<Role> roleList = roleService.list();
        return Result.fastSuccess(roleList);
    }

    /**
     * Gets system roles by page.
     *
     * @param name     the name
     * @param currPage the curr page
     * @param pageSize the page size
     * @return the system roles by page
     */
    @GetMapping("/role")
    @ApiOperation("分页获取系统角色")
    public Result<?> getSystemRolesByPage(String name, Integer currPage, Integer pageSize) {
        Page<Role> rolePage = roleService.listByPage(name, currPage, pageSize);
        return Result.fastSuccess(rolePage);
    }

    /**
     * Gets role.
     *
     * @param id the id
     * @return the role
     */
    @GetMapping("/role/{id}")
    @ApiOperation("根据id获取角色")
    public Result<?> getRole(@PathVariable("id") Long id) {
        Role role = roleService.getById(id);
        return Result.fastSuccess(role);
    }

    /**
     * Del role result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/role/{id}")
    @ApiOperation("根据id删除角色")
    public Result<?> delRole(@PathVariable("id") Integer id) {
        Role role = roleService.deleteRoleReturnEntity(id);
        if (Objects.nonNull(role)) {
            operationLogService.recordLog(OperationLog.OperationName.ROLE_DELETE, role.getName());
        }
        return Result.fastSuccess();
    }

    /**
     * Ins role result.
     *
     * @param roleBo the role bo
     * @return the result
     */
    @PostMapping("/role")
    @ApiOperation("创建角色")
    public Result<?> insRole(@RequestBody RoleBo roleBo) {
        roleBo.setId(null);
        boolean flag = roleService.save(roleBo.bo2Entity());
        if (flag) {
            operationLogService.recordLog(OperationLog.OperationName.ROLE_ADD, roleBo.getName());
            return Result.fastSuccess();
        }
        return new Result.Builder<String>().code(500).message("insert fail.").build();
    }

    /**
     * Update role result.
     *
     * @param roleBo the role bo
     * @return the result
     */
    @PutMapping("/role")
    @ApiOperation("修改角色")
    public Result<?> updateRole(@RequestBody RoleBo roleBo) {
        boolean flag = roleService.updateById(roleBo.bo2Entity());
        if (flag) {
            operationLogService.recordLog(OperationLog.OperationName.ROLE_UPDATE, roleBo.getName());
            return Result.fastSuccess();
        }
        return new Result.Builder<String>().code(500).message("update fail.").build();
    }

    /**
     * Gets role all permission.
     *
     * @param roleId the role id
     * @return the role all permission
     */
    @GetMapping("/role/permission/{roleId}")
    @ApiOperation("获取角色的权限关系")
    public Result<?> getRoleAllPermission(@PathVariable Long roleId) {
        List<RolePermission> rolePermissionList = roleService.getRolePermission(roleId);
        return Result.fastSuccess(rolePermissionList);
    }

    /**
     * Delete role permission result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/role/permission/{id}")
    @ApiOperation("删除角色权限")
    public Result<?> deleteRolePermission(@PathVariable Long id) {
        roleService.removeRolePermission(id);
        return Result.fastSuccess();
    }

    /**
     * Modify role permission result.
     *
     * @param roleId           the role id
     * @param permissionIdList the permission id list
     * @return the result
     */
    @PostMapping("/role/permission/{roleId}")
    @ApiOperation("修改角色的权限")
    public Result<?> modifyRolePermission(@PathVariable Integer roleId, @RequestBody List<Integer> permissionIdList) {
        roleService.updateRolePermission(roleId, permissionIdList);
        return Result.fastSuccess();
    }
}
