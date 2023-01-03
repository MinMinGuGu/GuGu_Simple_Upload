package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.AccountBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.AccountVo;

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
     * @param accountBo the account bo
     * @return the list
     */
    List<AccountVo> findByRequest(AccountBo accountBo);

    /**
     * Add account result.
     *
     * @param accountBo the account bo
     * @return the result
     */
    Boolean addAccount(AccountBo accountBo);

    /**
     * 删除用户时返回删除数据
     *
     * @param id id
     * @return 用户 account
     */
    Account deleteAccountReturnEntity(Integer id);
}
