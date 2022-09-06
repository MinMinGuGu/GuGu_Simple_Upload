package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.dto.AccountDto;
import com.gugu.upload.common.query.AccountQueryRequest;
import com.gugu.upload.common.vo.system.account.AccountVo;
import com.gugu.upload.service.IAccountService;
import io.swagger.annotations.Api;
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
    public Result<?> addAccount(@RequestBody AccountDto accountDto) {
        return accountService.addAccount(accountDto);
    }

    @PutMapping
    public Result<?> updateAccount(@RequestBody AccountDto accountDto) {
        // todo 有缺陷 万一直接调API修改系统默认角色呢
        boolean updateFlag = accountService.updateById(accountDto.dto2Entity());
        return updateFlag ? Result.fastSuccess() : new Result.Builder<String>().code(500).message("update fail.").build();
    }

    @DeleteMapping
    public Result<?> deleteAccount(@RequestBody AccountDto accountDto) {
        // todo 有缺陷 万一直接调API修改系统默认角色呢
        boolean deleteFlag = accountService.removeById(accountDto.getId());
        return deleteFlag ? Result.fastSuccess() : new Result.Builder<String>().code(500).message("update fail.").build();
    }
}