package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * List by page page.
     *
     * @param name     the name
     * @param currPage the curr page
     * @param pageSize the page size
     * @return the page
     */
    Page<Role> listByPage(String name, Integer currPage, Integer pageSize);

    /**
     * Add role permission.
     *
     * @param rolePermission the role permission
     */
    void addRolePermission(RolePermission rolePermission);

    /**
     * Remove role permission.
     *
     * @param id the id
     */
    void removeRolePermission(Long id);

    /**
     * Gets role permission.
     *
     * @param roleId the role id
     * @return the role permission
     */
    List<RolePermission> getRolePermission(Long roleId);

    /**
     * Update role permission.
     *
     * @param roleId           the role id
     * @param permissionIdList the permission id list
     */
    void updateRolePermission(Integer roleId, List<Integer> permissionIdList);

    /**
     * 删除角色时返回删除数据
     *
     * @param id id
     * @return 角色 role
     */
    Role deleteRoleReturnEntity(Integer id);
}
