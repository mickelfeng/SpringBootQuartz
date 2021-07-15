package com.godfunc.quartz;

import com.godfunc.entity.JobEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public abstract class AbstractJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobEntity job = new JobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(JobEntity.TASK_PROPERTIES), job);
        try {
            before(context, job);

            doExecute(context, job);

            after(context, job, null);
        } catch (Exception e) {
            log.error("执行任务异常", e);
            after(context, job, e);
        }
    }

    protected void before(JobExecutionContext context, JobEntity job) {

    }

    protected void after(JobExecutionContext context, JobEntity job, Exception e) {

    }

    public abstract void doExecute(JobExecutionContext context, JobEntity job) throws Exception;
}
