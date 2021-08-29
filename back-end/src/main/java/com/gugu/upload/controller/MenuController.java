package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.Menu;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.vo.MenuVo;
import com.gugu.upload.service.IMenuService;
import com.gugu.upload.service.IRoleService;
import com.gugu.upload.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Menu controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/28
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;

    @GetMapping
    public Result<?> menu(HttpServletRequest request) {
        Account currentAccount = LoginUtil.getCurrentAccount(request);
        log.info("Current user: {}", currentAccount);
        if (checkAccount(currentAccount)) {
            return Result.fastFail("Unable to get the current account, please log in again");
        }
        Permission accountPermission = getPermissionByAccount(currentAccount);
        log.info("The current account permissions are queried as: {}", accountPermission);
        List<MenuVo> menuVoList = getMenuVoListByPermission(accountPermission);
        log.info("The queried menuVoList is: {}", menuVoList);
        if (checkMenuList(menuVoList)) {
            return Result.fastFail("The current account does not have permission");
        }
        return new Result.Builder<List<MenuVo>>().success(menuVoList).build();
    }

    private boolean checkMenuList(List<MenuVo> menuVoList) {
        return menuVoList == null || menuVoList.isEmpty();
    }

    private List<MenuVo> getMenuVoListByPermission(Permission accountPermission) {
        List<Menu> menuByPermission = menuService.getMenuByPermission(accountPermission);
        if (menuByPermission == null || menuByPermission.isEmpty()) {
            return null;
        }
        Map<Integer, MenuVo> baseMenuVoMap = menuByPermission.stream().collect(Collectors.toMap(Menu::getId, this::generateMenuVo));
        Map<Integer, Menu> baseMenuMap = menuByPermission.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
        for (Map.Entry<Integer, Menu> entry : baseMenuMap.entrySet()) {
            Menu parentMenu = baseMenuMap.get(entry.getValue().getParentMenuId());
            if (parentMenu != null){
                MenuVo menuVo = baseMenuVoMap.get(parentMenu.getId());
                List<MenuVo> children = menuVo.getChildren();
                children.add(generateMenuVo(entry.getValue()));
                baseMenuVoMap.remove(entry.getValue().getId());
            }
        }
        return new LinkedList<>(baseMenuVoMap.values());
    }

    private MenuVo generateMenuVo(Menu menu){
        MenuVo menuVo = new MenuVo(menu);
        menuVo.setChildren(new LinkedList<>());
        return menuVo;
    }

    private Permission getPermissionByAccount(Account currentAccount) {
        Integer roleId = currentAccount.getRoleId();
        return roleService.getPermissionByRoleId(roleId);
    }

    private boolean checkAccount(Account currentAccount) {
        return currentAccount == null;
    }
}
