package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.IBo2Entity;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.Role;
import com.gugu.upload.common.query.ISupportQuery;
import com.gugu.upload.common.vo.system.account.AccountVo;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.service.IRoleService;
import com.gugu.upload.utils.TransformUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The type Account service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Service
public class AccountServiceImpl extends ServiceImpl<IAccountMapper, Account> implements IAccountService {

    @Resource
    private IFileService fileService;

    @Resource
    private IRoleService roleService;

    @Override
    public Integer getUserAllFileCount(Account currentAccount) {
        Integer accountId = currentAccount.getId();
        return fileService.getFileCountByAccountId(accountId);
    }

    @Override
    public List<AccountVo> findByRequest(ISupportQuery<Account> accountQueryRequest) {
        QueryWrapper<Account> accountQueryWrapper = accountQueryRequest.toQueryWrapper();
        List<Account> accountList = getBaseMapper().selectList(accountQueryWrapper);
        List<AccountVo> accountVoList = new LinkedList<>();
        accountList.forEach(item -> {
            Integer roleId = item.getRoleId();
            Role role = roleService.getById(roleId);
            AccountVo accountVo = TransformUtil.transform(item, AccountVo.class);
            accountVo.setRoleName(role.getName());
            accountVoList.add(accountVo);
        });
        return accountVoList;
    }

    @Override
    public Boolean addAccount(IBo2Entity<Account> accountDto) {
        Account account = accountDto.bo2Entity();
        return this.save(account);
    }

    @Override
    public Account deleteAccountReturnEntity(Integer id) {
        Account account = this.getById(id);
        if (Objects.isNull(account)) {
            return null;
        }
        this.removeById(id);
        return account;
    }
}
