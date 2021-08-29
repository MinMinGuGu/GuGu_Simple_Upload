package com.gugu.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gugu.upload.common.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Permission mapper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
@Mapper
public interface IPermissionMapper extends BaseMapper<Permission> {
}
