package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.entity.RolePermission;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.mapper.IRoleMapper;
import com.gugu.upload.mapper.IRolePermissionMapper;
import com.gugu.upload.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The type Role service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleMapper, Role> implements IRoleService {

    @Resource
    private IRolePermissionMapper rolePermissionMapper;

    @Resource
    private IPermissionMapper permissionMapper;

    @Override
    public Permission getPermissionByRoleId(Integer roleId) {
        RolePermission entityWrappers = new RolePermission();
        entityWrappers.setRoleId(roleId);
        QueryWrapper<RolePermission> rolePermissionQueryChainWrapper = Wrappers.query(entityWrappers);
        RolePermission rolePermission = rolePermissionMapper.selectOne(rolePermissionQueryChainWrapper);
        Integer permissionId = rolePermission.getPermissionId();
        return permissionMapper.selectById(permissionId);
    }
}
