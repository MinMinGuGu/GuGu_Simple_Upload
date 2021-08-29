package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.Constant;
import com.gugu.upload.common.entity.Menu;
import com.gugu.upload.common.entity.MenuPermission;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.mapper.IMenuMapper;
import com.gugu.upload.mapper.IMenuPermissionMapper;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Menu service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
@Service
public class MenuServiceImpl extends ServiceImpl<IMenuMapper, Menu> implements IMenuService {

    @Resource
    private IMenuPermissionMapper menuPermissionMapper;

    @Resource
    private IPermissionMapper permissionMapper;

    @Override
    public List<Menu> getMenuByPermission(Permission permission) {
        List<Menu> menuList = this.list();
        return menuList.stream().filter(menu -> this.getMenuByFilter(menu, permission)).collect(Collectors.toList());
    }

    private boolean getMenuByFilter(Menu menu, Permission permission){
        if (checkPermissionByAll(permission)){
            return true;
        }
        if (checkPermissionByNull(permission)){
            return false;
        }
        MenuPermission entityWrapper = new MenuPermission();
        entityWrapper.setMenuId(menu.getId());
        QueryWrapper<MenuPermission> query = Wrappers.query(entityWrapper);
        MenuPermission menuPermission = menuPermissionMapper.selectOne(query);
        return checkPermission(menuPermission, permission);
    }

    private boolean checkPermissionByNull(Permission permission) {
        return permission.getName().equals(Constant.NON_PERMISSION);
    }

    private boolean checkPermissionByAll(Permission permission) {
        return permission.getName().equals(Constant.ALL_PERMISSION);
    }

    private boolean checkPermission(MenuPermission menuPermission, Permission permission){
        Permission permissionByMenu = permissionMapper.selectById(menuPermission.getPermissionId());
        return permission.equals(permissionByMenu);
    }
}
