package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.mapper.IOperationLogMapper;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.TomcatUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Visit service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<IOperationLogMapper, OperationLog> implements IOperationLogService {
    @Override
    public void recordLog(OperationLog.OperationType operationType, String context) {
        HttpServletRequest httpServletRequest = TomcatUtil.getHttpRequestFroCurrThread();
        String ipAddress = IpUtil.getIpAddress(httpServletRequest);
        Account currentAccount = LoginHelper.getCurrentAccount(httpServletRequest);
        OperationLog operationLog = new OperationLog();
        operationLog.setIpAddress(ipAddress);
        operationLog.setOperationType(operationType);
        operationLog.setUserName(currentAccount.getUserName());
        operationLog.setContext(context);
        this.save(operationLog);
    }
}
