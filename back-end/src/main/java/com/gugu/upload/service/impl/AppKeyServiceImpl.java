package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.AppKeyBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.common.exception.OperationException;
import com.gugu.upload.common.exception.ParamsException;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IAppKeyMapper;
import com.gugu.upload.service.IAppKeyService;
import com.gugu.upload.utils.TransformUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type App key service.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Service
public class AppKeyServiceImpl extends ServiceImpl<IAppKeyMapper, AppKey> implements IAppKeyService {

    private final static Integer DEFAULT_CREATE_COUNT = 5;

    @Resource
    private IAccountMapper accountMapper;

    @Override
    public AppKey createAppKeyForAccount(AppKeyBo appKeyBo) {
        String userName = appKeyBo.getUserName();
        if (Objects.isNull(userName)) {
            throw new ParamsException("userName is null");
        }
        QueryWrapper<Account> accountQuery = Wrappers.query();
        accountQuery.eq("user_name", userName);
        Account account = accountMapper.selectOne(accountQuery);
        if (Objects.isNull(account)) {
            throw new ParamsException("不存在此用户名");
        }
        QueryWrapper<AppKey> query = Wrappers.query();
        query.eq("user_name", account.getUserName());
        int count = this.count(query);
        if (count >= DEFAULT_CREATE_COUNT) {
            throw new OperationException("每个用户只能创建" + DEFAULT_CREATE_COUNT + "个AppKey");
        }
        AppKey appKey = TransformUtil.transform(appKeyBo, AppKey.class);
        appKey.setValue(UUID.randomUUID().toString().replace("-", ""));
        this.save(appKey);
        return appKey;
    }

    @Override
    public AppKey deleteAppKeyReturnEntity(Integer id) {
        AppKey appKey = this.getById(id);
        if (Objects.isNull(appKey)) {
            return null;
        }
        this.removeById(id);
        return appKey;
    }

    @Override
    public List<AppKey> selectByUserName(AppKeyBo appKeyBo) {
        List<AppKey> appKeyList;
        String userName = appKeyBo.getUserName();
        if (StringUtils.hasText(userName)) {
            QueryWrapper<AppKey> query = Wrappers.query();
            query.like("user_name", userName);
            appKeyList = this.getBaseMapper().selectList(query);
        } else {
            appKeyList = this.list();
        }
        return appKeyList;
    }

    @Override
    public Page<AppKey> selectByPage(AppKeyBo appKeyBo) {
        String userName = appKeyBo.getUserName();
        QueryWrapper<AppKey> query = null;
        if (StringUtils.hasText(userName)) {
            query = Wrappers.query();
            query.like("user_name", userName);
        }
        return this.getBaseMapper().selectPage(Page.of(appKeyBo.getCurrPage(), appKeyBo.getPageSize()), query);
    }
}
