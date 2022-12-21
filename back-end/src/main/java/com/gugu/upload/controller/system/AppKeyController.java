package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.bo.AppKeyBo;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.service.IAppKeyService;
import com.gugu.upload.service.IOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * The type App key controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Api("系统AppKey相关")
@Slf4j
@RestController
@RequestMapping("/system/appKey")
@PermissionCheck
public class AppKeyController {

    @Resource
    private IAppKeyService appKeyService;

    @Resource
    private IOperationLogService operationLogService;

    /**
     * Get all app key result.
     *
     * @param appKeyBo the app key bo
     * @return the result
     */
    @GetMapping
    @ApiOperation("获取AppKey")
    public Result<?> getAllAppKey(AppKeyBo appKeyBo) {
        // 是否需要分页
        if (appKeyBo.getCurrPage() == null || appKeyBo.getPageSize() == null) {
            return Result.fastSuccess(appKeyService.selectByUserName(appKeyBo));
        }
        return Result.fastSuccess(appKeyService.selectByPage(appKeyBo));
    }

    /**
     * Create app key result.
     *
     * @param appKeyBo the app key bo
     * @return the result
     */
    @PostMapping
    @ApiOperation("创建AppKey")
    public Result<?> createAppKey(@RequestBody AppKeyBo appKeyBo) {
        AppKey appKeyVo = appKeyService.createAppKeyForAccount(appKeyBo);
        operationLogService.recordLog(OperationLog.OperationType.APP_KEY_ADD, appKeyBo.getUserName());
        return Result.fastSuccess(appKeyVo);
    }

    /**
     * Delete app key result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除AppKey")
    public Result<?> deleteAppKey(@PathVariable Integer id) {
        AppKey appKey = appKeyService.deleteAppKeyReturnEntity(id);
        if (Objects.nonNull(appKey)) {
            operationLogService.recordLog(OperationLog.OperationType.APP_KEY_DELETE, appKey.getUserName());
            return Result.fastSuccess();
        }
        return Result.fastFail("不存在此AppKey");
    }
}
