package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.AppKeyBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.common.exception.OperationException;
import com.gugu.upload.common.exception.ParamsException;
import com.gugu.upload.common.vo.AppKeyVo;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IAppKeyMapper;
import com.gugu.upload.service.IAppKeyService;
import com.gugu.upload.utils.MapUtil;
import com.gugu.upload.utils.TransformUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
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
    public AppKeyVo createAppKeyForAccount(AppKeyBo appKeyBo) {
        String userName = appKeyBo.getUserName();
        if (Objects.isNull(userName)) {
            throw new ParamsException("userName is null");
        }
        Account account = getAccountForUserName(userName);
        QueryWrapper<AppKey> query = Wrappers.query();
        query.eq("user_id", account.getId());
        int count = this.count(query);
        if (count >= DEFAULT_CREATE_COUNT) {
            throw new OperationException("每个用户只能创建" + DEFAULT_CREATE_COUNT + "个AppKey");
        }
        AppKey appKey = TransformUtil.transform(appKeyBo, AppKey.class);
        appKey.setValue(UUID.randomUUID().toString().replace("-", ""));
        appKey.setUserId(account.getId());
        this.save(appKey);
        AppKeyVo appKeyVo = TransformUtil.transform(appKey, AppKeyVo.class);
        appKeyVo.setUserName(account.getUserName());
        return appKeyVo;
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
    public List<AppKeyVo> selectForUserName(String userName) {
        List<AppKeyVo> appKeyVoList = new LinkedList<>();
        List<AppKey> appKeyList;
        if (StringUtils.hasText(userName)) {
            Account account = getAccountForUserName(userName);
            appKeyList = getBaseMapper().selectByMap(MapUtil.toMap("user_id", account.getId()));
        } else {
            appKeyList = this.list();
        }
        for (AppKey appKey : appKeyList) {
            Account account = accountMapper.selectById(appKey.getUserId());
            AppKeyVo appKeyVo = TransformUtil.transform(appKey, AppKeyVo.class);
            appKeyVo.setUserName(account.getUserName());
            appKeyVoList.add(appKeyVo);
        }
        return appKeyVoList;
    }

    private Account getAccountForUserName(String userName) {
        List<Account> accountList = accountMapper.selectByMap(MapUtil.toMap("user_name", userName));
        if (CollectionUtils.isEmpty(accountList)) {
            throw new ParamsException("不存在此用户");
        }
        // 设计上userName唯一
        return accountList.get(0);
    }
}
