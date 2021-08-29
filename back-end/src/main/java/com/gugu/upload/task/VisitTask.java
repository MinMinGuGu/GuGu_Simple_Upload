package com.gugu.upload.task;

import com.gugu.upload.common.entity.Visit;
import com.gugu.upload.service.IVisitService;
import com.gugu.upload.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Visit task.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
@Slf4j
@Component
public class VisitTask {

    @Resource
    private IVisitService iVisitService;

    /**
     * Save visit.
     */
    @Scheduled(cron = "0 0/3 * * * ? ")
    public void saveVisit(){
        List<Visit> visits = CacheUtil.get(Visit.class);
        visits.forEach(iVisitService::save);
    }
}
