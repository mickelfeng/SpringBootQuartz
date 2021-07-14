package com.godfunc.quartz;

import com.godfunc.entity.JobEntity;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

@DisallowConcurrentExecution
public class DisallowConcurrentJob extends AbstractJob {
    @Override
    public void doExecute(JobExecutionContext context, JobEntity job) {
        JobInvokeUtils.invoke(job);
    }
}
