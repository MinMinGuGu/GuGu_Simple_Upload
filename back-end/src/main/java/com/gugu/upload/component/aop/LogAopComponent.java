package com.gugu.upload.component.aop;

import com.gugu.upload.common.annotation.LogAnnotation;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.exception.UnknownException;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.TomcatUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志注解解析
 *
 * @author minmin
 * @date 2022/12/19
 */
@Slf4j
@Aspect
@Component
public class LogAopComponent {

    @Resource
    private IOperationLogService operationLogService;

    @Pointcut("@annotation(com.gugu.upload.common.annotation.LogAnnotation)")
    private void logAnnotation() {
    }

    @After("logAnnotation()")
    public void recordLog(JoinPoint joinPoint) {
        LogAnnotation logAnnotation = getLogAnnotationForJoinPoint(joinPoint);
        OperationLog operationLog = generateOperationLog(logAnnotation);
        operationLogService.save(operationLog);
    }

    private OperationLog generateOperationLog(LogAnnotation logAnnotation) {
        HttpServletRequest httpServletRequest = TomcatUtil.getHttpRequestFroCurrThread();
        String ipAddress = IpUtil.getIpAddress(httpServletRequest);
        OperationLog.OperationType operationType = logAnnotation.value();
        Account currentAccount = LoginHelper.getCurrentAccount(httpServletRequest);
        String userName = currentAccount.getUserName();
        OperationLog operationLog = new OperationLog();
        operationLog.setIpAddress(ipAddress);
        operationLog.setOperationType(operationType);
        operationLog.setUserName(userName);
        return operationLog;
    }

    private LogAnnotation getLogAnnotationForJoinPoint(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        if (Objects.isNull(annotation)) {
            throw new UnknownException("无法解析到LogAnnotation注解");
        }
        return annotation;
    }
}
