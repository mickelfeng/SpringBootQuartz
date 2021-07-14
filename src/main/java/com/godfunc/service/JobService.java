package com.godfunc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.godfunc.entity.JobEntity;
import com.godfunc.mapper.JobMapper;
import com.godfunc.quartz.ScheduleUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JobService extends ServiceImpl<JobMapper, JobEntity> {

    private final static Logger log = LoggerFactory.getLogger(JobService.class);

    private final Scheduler scheduler;

    public JobService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean add(JobEntity job) {
        if (save(job)) {
            try {
                ScheduleUtils.create(scheduler, job);
                return true;
            } catch (SchedulerException e) {
                log.error("创建任务异常", e);
            }
        }
        return false;
    }

    public boolean edit(JobEntity job) {
        if (updateById(job)) {
            try {
                if (ScheduleUtils.checkExists(scheduler, job)) {
                    ScheduleUtils.delete(scheduler, job);

                }
                ScheduleUtils.create(scheduler, job);
                return true;
            } catch (SchedulerException e) {
                log.error("修改任务时删除任务失败", e);
            }
        }
        return false;
    }

    public boolean remove(JobEntity job) {
        if(removeById(job)) {
            try {
                return ScheduleUtils.delete(scheduler, job);
            } catch (SchedulerException e) {
                log.error("删除任务失败", e);
            }
        }
        return false;
    }
}
