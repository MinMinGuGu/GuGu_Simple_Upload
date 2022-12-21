package com.gugu.upload.common.annotation;

import com.gugu.upload.common.entity.Permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 * 负责解析的类为{@link com.gugu.upload.component.aop.PermissionAopComponent}
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PermissionCheck {
    /**
     * Value permission . permission enum.
     *
     * @return the permission . permission enum
     */
    Permission.PermissionEnum value() default Permission.PermissionEnum.MANAGE;
}
