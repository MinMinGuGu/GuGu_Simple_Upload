package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.bo.AccountBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.common.vo.AccountVo;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.ILoginService;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.MapUtil;
import com.gugu.upload.utils.TransformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Account controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/16
 * @since 1.8
 */
@Api("系统用户管理")
@Slf4j
@RestController
@RequestMapping("/system/account")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @Resource
    private ILoginService loginService;

    @Resource
    private IOperationLogService operationLogService;

    /**
     * Find user list result.
     *
     * @param accountBo the account bo
     * @return the result
     */
    @GetMapping
    @ApiOperation("查询用户")
    @PermissionCheck
    public Result<?> findUserList(AccountBo accountBo) {
        List<AccountVo> accountList = accountService.findByRequest(accountBo);
        return Result.fastSuccess(accountList);
    }

    /**
     * Add account result.
     *
     * @param accountBo the account dto
     * @return the result
     */
    @PostMapping
    @ApiOperation("添加用户")
    @PermissionCheck
    public Result<?> addAccount(@RequestBody AccountBo accountBo) {
        Boolean result = accountService.addAccount(accountBo);
        if (result) {
            operationLogService.recordLog(OperationLog.OperationName.USER_ADD, accountBo.getUsername());
            return Result.fastSuccess();
        }
        return Result.fastFail("Save account fail.");
    }

    /**
     * Update account result.
     *
     * @param accountBo the account dto
     * @return the result
     */
    @PutMapping
    @ApiOperation("修改用户")
    @PermissionCheck
    public Result<?> updateAccount(@RequestBody AccountBo accountBo) {
        boolean updateFlag = accountService.updateById(TransformUtil.transform(accountBo, Account.class));
        if (updateFlag) {
            operationLogService.recordLog(OperationLog.OperationName.USER_UPDATE, accountBo.getUsername());
            return Result.fastSuccess();
        }
        return new Result.Builder<String>().code(500).message("update fail.").build();
    }

    /**
     * Delete account result.
     *
     * @param accountBo the account dto
     * @return the result
     */
    @DeleteMapping
    @ApiOperation("删除用户")
    @PermissionCheck
    public Result<?> deleteAccount(@RequestBody AccountBo accountBo) {
        Account account = accountService.deleteAccountReturnEntity(accountBo.getId());
        operationLogService.recordLog(OperationLog.OperationName.FILE_DELETE, account.getUserName());
        return Result.fastSuccess();
    }

    /**
     * Gets account file upload info.
     *
     * @param request the request
     * @return the account file upload info
     */
    @GetMapping("/fileUpload/info")
    @ApiOperation("获取用户在系统上传的文件数量")
    @PermissionCheck(Permission.PermissionEnum.UPLOAD)
    public Result<?> getAccountFileUploadInfo(HttpServletRequest request) {
        Account currentAccount = loginService.getCurrentAccount(request);
        int userFileUploadCount = accountService.getUserAllFileCount(currentAccount);
        return Result.fastSuccess(MapUtil.toMap("userFileUploadCount", userFileUploadCount));
    }
}
