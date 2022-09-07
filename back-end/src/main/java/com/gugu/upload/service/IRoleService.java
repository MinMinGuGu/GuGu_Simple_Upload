package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.entity.RolePermission;

import java.util.List;

/**
 * The interface Role service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
public interface IRoleService extends IService<Role> {
    /**
     * Gets permission by role.
     *
     * @param roleId the role id
     * @return the permission by role
     */
    Permission getPermissionByRoleId(Integer roleId);

    /**
     * List by page page.
     *
     * @param currPage the curr page
     * @param pageSize the page size
     * @return the page
     */
    Page<Role> listByPage(String name, Integer currPage, Integer pageSize);

    void addRolePermission(RolePermission rolePermission);

    void removeRolePermission(Long id);

    void removeRoleById(Long id);

    List<RolePermission> getRolePermission(Long roleId);

    void updateRolePermission(Integer roleId, List<Integer> permissionIdList);
}
