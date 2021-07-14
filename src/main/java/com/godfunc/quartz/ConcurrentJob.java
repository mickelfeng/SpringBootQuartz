package com.godfunc.quartz;

import com.godfunc.entity.JobEntity;
import org.quartz.JobExecutionContext;

public class ConcurrentJob extends AbstractJob {
    @Override
    public void doExecute(JobExecutionContext context, JobEntity job) {
        JobInvokeUtils.invoke(job);
    }
}
