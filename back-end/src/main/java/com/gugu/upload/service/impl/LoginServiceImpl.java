package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gugu.upload.common.bo.LoginBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.LoginVo;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.service.ILoginService;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.MD5Util;
import com.gugu.upload.utils.SessionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Login service.
 *
 * @author minmin
 * @version 1.0
 * @date 2023 /01/04
 * @since 1.8
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IAccountMapper accountMapper;

    @Resource
    private IPermissionMapper permissionMapper;

    @Override
    public boolean checkBo(LoginBo loginBo) {
        return loginBo.getUsername() == null || loginBo.getPassword() == null;
    }

    @Override
    public boolean check(Account account) {
        return account == null || account.getUserName() == null || account.getUserPassword() == null;
    }

    @Override
    public boolean checkCache(HttpServletRequest httpServletRequest) {
        return CacheUtil.isThere(SessionUtil.getKeyBySessionId(httpServletRequest));
    }

    @Override
    public Account findAccount(LoginBo loginBo) {
        QueryWrapper<Account> query = Wrappers.query();
        query.eq("user_name", loginBo.getUsername()).eq("user_password", MD5Util.encrypt(loginBo.getPassword()));
        return accountMapper.selectOne(query);
    }

    @Override
    public LoginVo saveBySession(LoginBo loginBo, Account account, HttpServletRequest httpServletRequest) {
        CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(account);
        if (Boolean.TRUE.toString().equalsIgnoreCase(loginBo.getRememberMe())) {
            cacheObject.setTime(24, CacheUtil.CacheObject.TimeUnit.HOUR);
        }
        CacheUtil.pull(SessionUtil.getKeyBySessionId(httpServletRequest), cacheObject);
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(account.getUserName());
        loginVo.setPermissionList(permissionMapper.getPermissionByAccountId(account.getId()));
        return loginVo;
    }
}
