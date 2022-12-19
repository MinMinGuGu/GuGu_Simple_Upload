package com.gugu.upload.common.annotation;

import com.gugu.upload.common.entity.OperationLog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录日志注解
 *
 * @author minmin
 * @date 2022/12/19
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {
    OperationLog.OperationType value() default OperationLog.OperationType.UNKNOWN;
}
