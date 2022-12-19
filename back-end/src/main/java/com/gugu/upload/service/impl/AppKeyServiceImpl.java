package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.AppKeyBo;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.common.exception.OperationException;
import com.gugu.upload.common.exception.ParamsException;
import com.gugu.upload.mapper.IAppKeyMapper;
import com.gugu.upload.service.IAppKeyService;
import com.gugu.upload.utils.TransformUtil;
import org.springframework.stereotype.Service;

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

    @Override
    public AppKey createAppKeyForAccount(AppKeyBo appKeyBo) {
        Integer userId = appKeyBo.getUserId();
        if (Objects.isNull(userId)) {
            throw new ParamsException("userId is null");
        }
        QueryWrapper<AppKey> query = Wrappers.query();
        query.eq("user_id", userId);
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
}
