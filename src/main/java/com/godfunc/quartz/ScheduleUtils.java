package com.godfunc.quartz;

import com.godfunc.entity.JobEntity;
import com.godfunc.enums.JobConcurrentEnum;
import com.godfunc.enums.JobStatusEnum;
import org.quartz.*;

public class ScheduleUtils {

    private final static String JOB_ID_PREFIX = "TASK_ID_";


    /**
     * 默认
     */
    public static final int MISFIRE_DEFAULT = 0;

    /**
     * 立即触发执行
     */
    public static final int MISFIRE_IGNORE_MISFIRES = 1;

    /**
     * 触发一次执行
     */
    public static final int MISFIRE_FIRE_AND_PROCEED = 2;

    /**
     * 不触发立即执行
     */
    public static final int MISFIRE_DO_NOTHING = 3;


    public static JobKey getJobKey(String jobId, String jobGroup) {
        return JobKey.jobKey(JOB_ID_PREFIX + jobId, jobGroup);

    }

    public static void create(Scheduler scheduler, JobEntity job) throws SchedulerException {
        Class<? extends Job> jobClass = job.getConcurrent() == JobConcurrentEnum.CONCURRENT.getValue() ? ConcurrentJob.class : DisallowConcurrentJob.class;
        String jobId = job.getId().toString();
        String jobGroup = job.getGroupJob();
        JobKey jobKey = getJobKey(jobId, jobGroup);

        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder)
                .build();
        jobDetail.getJobDataMap().put(JobEntity.TASK_PROPERTIES, job);

        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        scheduler.scheduleJob(jobDetail, cronTrigger);

        if (job.getStatus() == JobStatusEnum.PAUSE.getValue()) {
            scheduler.pauseJob(jobKey);
        }
    }


    public static void resume(Scheduler scheduler, JobEntity job) throws SchedulerException {
        scheduler.resumeJob(getJobKey(job.getId().toString(), job.getGroupJob()));
    }

    public static void pause(Scheduler scheduler, JobEntity job) throws SchedulerException {
        scheduler.pauseJob(getJobKey(job.getId().toString(), job.getGroupJob()));
    }

    public static boolean delete(Scheduler scheduler, JobEntity job) throws SchedulerException {
        return scheduler.deleteJob(getJobKey(job.getId().toString(), job.getGroupJob()));
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobEntity job, CronScheduleBuilder cb) {
        switch (job.getMisfire()) {
            case MISFIRE_DEFAULT:
                return cb;
            case MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                return cb;
        }
    }


    public static boolean checkExists(Scheduler scheduler, JobEntity job) throws SchedulerException {
        return scheduler.checkExists(getJobKey(job.getId().toString(), job.getGroupJob()));
    }
}
