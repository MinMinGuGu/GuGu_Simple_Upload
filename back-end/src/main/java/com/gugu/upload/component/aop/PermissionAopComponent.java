package com.gugu.upload.component.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.entity.RolePermission;
import com.gugu.upload.common.exception.PermissionException;
import com.gugu.upload.common.exception.UnknownException;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.mapper.IRoleMapper;
import com.gugu.upload.mapper.IRolePermissionMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 解析权限注解AOP
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Aspect
@Component
public class PermissionAopComponent {

    @Resource
    private IPermissionMapper permissionMapper;

    @Resource
    private IRoleMapper roleMapper;

    @Resource
    private IRolePermissionMapper rolePermissionMapper;

    @Pointcut("@within(com.gugu.upload.common.annotation.PermissionCheck)")
    private void permissionAopPointCut() {
    }

    /**
     * Check permission.
     *
     * @param joinPoint the join point
     */
    @Before("permissionAopPointCut()")
    public void checkPermission(JoinPoint joinPoint) {
        Account currentAccount = LoginHelper.getCurrentAccount();
        PermissionCheck permissionCheck = getPermissionCheck(joinPoint);
        if (check(currentAccount, permissionCheck)) {
            throw new PermissionException("Insufficient Permissions.");
        }
    }

    private boolean check(Account currentAccount, PermissionCheck permissionCheck) {
        Permission accountPermission = getAccountPermission(currentAccount);
        int accountPermissionScore = calculateAuthorityScore(accountPermission);
        return accountPermissionScore < permissionCheck.value().getScore();
    }

    private int calculateAuthorityScore(Permission accountPermission) {
        for (Permission.PermissionEnum permissionEnum : Permission.PermissionEnum.values()) {
            if (permissionEnum.getName().equals(accountPermission.getName())) {
                return permissionEnum.getScore();
            }
        }
        throw new UnknownException(accountPermission.getName() + "无法匹配" + Arrays.toString(Permission.PermissionEnum.values()));
    }

    private Permission getAccountPermission(Account currentAccount) {
        Role role = getAccountRole(currentAccount);
        return getPermissionForRole(role);
    }

    private Permission getPermissionForRole(Role role) {
        QueryWrapper<RolePermission> rolePermissionQuery = Wrappers.query();
        rolePermissionQuery.eq("role_id", role.getId());
        RolePermission rolePermission = rolePermissionMapper.selectOne(rolePermissionQuery);
        return permissionMapper.selectById(rolePermission.getPermissionId());
    }

    private Role getAccountRole(Account currentAccount) {
        return roleMapper.selectById(currentAccount.getRoleId());
    }

    private PermissionCheck getPermissionCheck(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Annotation[] declaredAnnotations = signature.getDeclaringType().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation instanceof PermissionCheck) {
                return (PermissionCheck) declaredAnnotation;
            }
        }
        throw new UnknownException("无法匹配到PermissionCheck注解");
    }
}
