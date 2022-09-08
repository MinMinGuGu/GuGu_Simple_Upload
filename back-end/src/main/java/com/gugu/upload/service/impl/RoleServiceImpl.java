package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.entity.RolePermission;
import com.gugu.upload.common.exception.ServiceException;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.mapper.IRoleMapper;
import com.gugu.upload.mapper.IRolePermissionMapper;
import com.gugu.upload.service.IRolePermissionService;
import com.gugu.upload.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    private static final String FIELD_ROLE_ID = "role_id";

    private static final String FIELD_ROLE_NAME = "name";

    private static final String MODIFY_SYSTEM_DEFAULT_FIELD_WARNING = "It is not allowed to modify the superAdmin role.";

    @Resource
    private IRolePermissionMapper rolePermissionMapper;

    @Resource
    private IPermissionMapper permissionMapper;

    @Resource
    private IRolePermissionService rolePermissionService;

    @Override
    public Permission getPermissionByRoleId(Integer roleId) {
        RolePermission entityWrappers = new RolePermission();
        entityWrappers.setRoleId(roleId);
        QueryWrapper<RolePermission> rolePermissionQueryChainWrapper = Wrappers.query(entityWrappers);
        RolePermission rolePermission = rolePermissionMapper.selectOne(rolePermissionQueryChainWrapper);
        Integer permissionId = rolePermission.getPermissionId();
        return permissionMapper.selectById(permissionId);
    }

    @Override
    public Page<Role> listByPage(String name, Integer currPage, Integer pageSize) {
        QueryWrapper<Role> roleQueryWrapper = null;
        if (StringUtils.hasText(name)) {
            roleQueryWrapper = Wrappers.query();
            roleQueryWrapper.like(FIELD_ROLE_NAME, name);
        }
        Page<Role> page = new Page<>(currPage, pageSize);
        return getBaseMapper().selectPage(page, roleQueryWrapper);
    }

    @Override
    public void addRolePermission(RolePermission rolePermission) {
        Role role = getBaseMapper().selectById(rolePermission.getRoleId());
        if (Boolean.TRUE.equals(role.getSystemDefault())) {
            throw new ServiceException(MODIFY_SYSTEM_DEFAULT_FIELD_WARNING);
        }
        int flag = rolePermissionMapper.updateById(rolePermission);
        if (flag < 1) {
            throw new ServiceException("The ID does not exist.");
        }
    }

    @Override
    public void removeRolePermission(Long id) {
        Role role = getBaseMapper().selectById(id);
        if (Boolean.TRUE.equals(role.getSystemDefault())) {
            throw new ServiceException(MODIFY_SYSTEM_DEFAULT_FIELD_WARNING);
        }
        int flag = rolePermissionMapper.deleteById(id);
        if (flag < 1) {
            throw new ServiceException("The ID does not exist.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRoleById(Long id) {
        getBaseMapper().deleteById(id);
        QueryWrapper<RolePermission> wrapper = Wrappers.query();
        wrapper.eq(FIELD_ROLE_ID, id);
        rolePermissionMapper.delete(wrapper);
    }

    @Override
    public List<RolePermission> getRolePermission(Long roleId) {
        QueryWrapper<RolePermission> query = Wrappers.query();
        query.eq(FIELD_ROLE_ID, roleId);
        return rolePermissionMapper.selectList(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRolePermission(Integer roleId, List<Integer> permissionIdList) {
        Role role = getBaseMapper().selectById(roleId);
        if (Role.DefaultRoleEnum.SUPER_ADMIN.getName().equals(role.getName())) {
            throw new ServiceException(MODIFY_SYSTEM_DEFAULT_FIELD_WARNING);
        }
        QueryWrapper<RolePermission> rolePermissionQuery = Wrappers.query();
        rolePermissionQuery.eq(FIELD_ROLE_ID, roleId);
        rolePermissionMapper.delete(rolePermissionQuery);
        List<Integer> permissionIds = permissionMapper.getIds();
        if (CollectionUtils.isNotEmpty(permissionIdList)) {
            List<RolePermission> rolePermissionList = permissionIdList.stream().filter(permissionIds::contains).map(permissionId -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                return rolePermission;
            }).collect(Collectors.toList());
            rolePermissionService.saveBatch(rolePermissionList);
        }
    }
}
