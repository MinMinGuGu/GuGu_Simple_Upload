package com.gugu.upload.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.OperationLogBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.exception.ServiceException;
import com.gugu.upload.controller.helper.HttpHelper;
import com.gugu.upload.mapper.IOperationLogMapper;
import com.gugu.upload.service.ILoginService;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.TomcatUtil;
import com.gugu.upload.utils.TransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Visit service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Slf4j
@Service
public class OperationLogServiceImpl extends ServiceImpl<IOperationLogMapper, OperationLog> implements IOperationLogService {

    private final static String XLSX_NAME = "GuGuSimpleUpload_OperationLog.xlsx";

    @Resource
    private ILoginService loginService;

    @Override
    public void recordLog(OperationLog.OperationName operationName, String context) {
        HttpServletRequest httpServletRequest = TomcatUtil.getHttpRequestFroCurrThread();
        String ipAddress = IpUtil.getIpAddress(httpServletRequest);
        Account currentAccount = loginService.getCurrentAccount(httpServletRequest);
        OperationLog operationLog = new OperationLog();
        operationLog.setIpAddress(ipAddress);
        operationLog.setOperationName(operationName.getDescription());
        operationLog.setUserName(currentAccount.getUserName());
        operationLog.setContext(context);
        this.save(operationLog);
    }

    @Override
    public Page<OperationLog> selectByPage(OperationLogBo operationLogBo) {
        OperationLog operationLog = TransformUtil.transform(operationLogBo, OperationLog.class);
        QueryWrapper<OperationLog> query = Wrappers.query(operationLog);
        return this.page(Page.of(operationLogBo.getCurrPage(), operationLogBo.getPageSize()), query);
    }

    @Override
    public void clearLog() {
        this.getBaseMapper().delete(null);
    }

    @Override
    public void export2HttpResponse(HttpServletResponse httpServletResponse) {
        try {
            HttpHelper.settingFileStreamHeader(httpServletResponse, XLSX_NAME);
            EasyExcel.write(httpServletResponse.getOutputStream(), OperationLog.class).sheet("操作日志").doWrite(this.list());
        } catch (IOException e) {
            log.error("获取响应流失败", e);
            throw new ServiceException(e);
        }
    }
}
