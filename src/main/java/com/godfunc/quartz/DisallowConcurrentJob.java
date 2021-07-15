package com.godfunc.quartz;

import com.godfunc.entity.JobEntity;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

@DisallowConcurrentExecution
// @PersistJobDataAfterExecution
public class DisallowConcurrentJob extends AbstractJob {
    @Override
    public void doExecute(JobExecutionContext context, JobEntity job) throws Exception {
//        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//        Integer count = (Integer) jobDataMap.getOrDefault("count", 0);
//        jobDataMap.put("count", count != null ? ++count : 0);
//        System.out.println(count);

        JobInvokeUtils.invoke(job);

    }
}
