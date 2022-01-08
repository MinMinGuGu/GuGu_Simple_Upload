package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.Account;

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
}
