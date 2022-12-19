package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.IBo2Entity;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.query.ISupportQuery;
import com.gugu.upload.common.vo.system.account.AccountVo;

import java.util.List;

/**
 * The interface Account service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public interface IAccountService extends IService<Account> {
    /**
     * Gets user all file count.
     *
     * @param currentAccount the current account
     * @return the user all file count
     */
    Integer getUserAllFileCount(Account currentAccount);

    /**
     * Find by request list.
     *
     * @param accountQueryRequest the account query request
     * @return the list
     */
    List<AccountVo> findByRequest(ISupportQuery<Account> accountQueryRequest);

    /**
     * Add account result.
     *
     * @param accountDto the account dto
     * @return the result
     */
    Boolean addAccount(IBo2Entity<Account> accountDto);

    /**
     * 删除用户时返回删除数据
     *
     * @param id id
     * @return 用户
     */
    Account deleteAccountReturnEntity(Integer id);
}
