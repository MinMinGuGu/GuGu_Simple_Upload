package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.Menu;
import com.gugu.upload.common.entity.Permission;

import java.util.List;

/**
 * The interface Menu service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
public interface IMenuService extends IService<Menu> {
    /**
     * Gets menu by permission.
     *
     * @param permission the permission
     * @return the menu by permission
     */
    List<Menu> getMenuByPermission(Permission permission);
}
