package com.gugu.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gugu.upload.common.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Role permission mapper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
@Mapper
public interface IRolePermissionMapper extends BaseMapper<RolePermission> {
}
