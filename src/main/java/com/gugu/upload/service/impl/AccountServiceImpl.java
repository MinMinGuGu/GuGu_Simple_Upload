package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 * The type Account service.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Service
public class AccountServiceImpl extends ServiceImpl<IAccountMapper, Account> implements IAccountService {

}
