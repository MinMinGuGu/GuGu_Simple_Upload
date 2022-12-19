package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.AppKeyBo;
import com.gugu.upload.common.entity.AppKey;

/**
 * The interface App key service.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
public interface IAppKeyService extends IService<AppKey> {
    /**
     * 根据创建appKey
     *
     * @param appKeyBo 参数
     * @return appKey app key
     */
    AppKey createAppKeyForAccount(AppKeyBo appKeyBo);


    /**
     * 删除AppKey并返回删除数据
     *
     * @param id id
     * @return 删除数据 app key
     */
    AppKey deleteAppKeyReturnEntity(Integer id);
}
