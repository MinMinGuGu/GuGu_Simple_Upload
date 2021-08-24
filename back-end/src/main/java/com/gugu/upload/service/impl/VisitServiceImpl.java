package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.Visit;
import com.gugu.upload.mapper.IVisitMapper;
import com.gugu.upload.service.IVisitService;
import org.springframework.stereotype.Service;

/**
 * The type Visit service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Service
public class VisitServiceImpl extends ServiceImpl<IVisitMapper, Visit> implements IVisitService {
}
