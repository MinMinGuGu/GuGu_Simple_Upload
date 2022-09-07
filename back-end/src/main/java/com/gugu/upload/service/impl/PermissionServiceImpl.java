package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Permission;
import com.gugu.upload.mapper.IPermissionMapper;
import com.gugu.upload.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * @author minmin
 * @date 2022/03/12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<IPermissionMapper, Permission> implements IPermissionService {
}
