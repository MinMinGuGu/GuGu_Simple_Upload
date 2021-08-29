package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.entity.Role;

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
}
