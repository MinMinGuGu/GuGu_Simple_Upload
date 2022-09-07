package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.RolePermission;
import com.gugu.upload.mapper.IRolePermissionMapper;
import com.gugu.upload.service.IRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * @author minmin
 * @date 2022/03/14
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<IRolePermissionMapper, RolePermission> implements IRolePermissionService {
}
