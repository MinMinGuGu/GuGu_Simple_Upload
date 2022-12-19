package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.bo.AccountBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.OperationLog;
import com.gugu.upload.common.query.AccountQueryRequest;
import com.gugu.upload.common.vo.system.account.AccountVo;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.IOperationLogService;
import com.gugu.upload.utils.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * fixme 存在删除系统默认账号风险 还需要补充接口文档
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
    private IOperationLogService operationLogService;

    /**
     * Find user list result.
     *
     * @param accountQueryRequest the account query request
     * @return the result
     */
    @GetMapping
    public Result<?> findUserList(@ModelAttribute AccountQueryRequest accountQueryRequest) {
        if (accountQueryRequest == null) {
            accountQueryRequest = new AccountQueryRequest();
        }
        List<AccountVo> accountList = accountService.findByRequest(accountQueryRequest);
        return Result.fastSuccess(accountList);
    }

    @PostMapping
    public Result<?> addAccount(@RequestBody AccountBo accountDto) {
        Boolean result = accountService.addAccount(accountDto);
        if (result) {
            operationLogService.recordLog(OperationLog.OperationType.USER_ADD, accountDto.getUsername());
            return Result.fastSuccess();
        }
        return Result.fastFail("Save account fail.");
    }

    @PutMapping
    public Result<?> updateAccount(@RequestBody AccountBo accountDto) {
        // todo 有缺陷 万一直接调API修改系统默认角色呢
        boolean updateFlag = accountService.updateById(accountDto.bo2Entity());
        if (updateFlag) {
            operationLogService.recordLog(OperationLog.OperationType.USER_UPDATE, accountDto.getUsername());
            return Result.fastSuccess();
        }
        return new Result.Builder<String>().code(500).message("update fail.").build();
    }

    @DeleteMapping
    public Result<?> deleteAccount(@RequestBody AccountBo accountDto) {
        // todo 有缺陷 万一直接调API修改系统默认角色呢
        Account account = accountService.deleteAccountReturnEntity(accountDto.getId());
        operationLogService.recordLog(OperationLog.OperationType.FILE_DELETE, account.getUserName());
        return Result.fastSuccess();
    }

    @GetMapping("/fileUpload/info")
    @ApiOperation("获取用户在系统上传的文件数量")
    public Result<?> getAccountFileUploadInfo(HttpServletRequest request) {
        Account currentAccount = LoginHelper.getCurrentAccount(request);
        int userFileUploadCount = accountService.getUserAllFileCount(currentAccount);
        return Result.fastSuccess(MapUtil.toMap("userFileUploadCount", userFileUploadCount));
    }
}
