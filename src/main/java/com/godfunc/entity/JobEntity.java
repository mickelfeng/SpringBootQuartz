package com.godfunc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_job")
public class JobEntity implements Serializable {

    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    @TableId
    private Long id;

    private String name;

    private String groupJob;

    private String invoke;

    private String cron;

    /**
     * misfire策略 0默认 1立即执行 2触发一次执行 3不触发执行
     */
    private Integer misfire;

    /**
     * 是否允许并发执行 0禁止 1允许
     */
    private Integer concurrent;

    /**
     * 状态 0停用 1启用
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupJob() {
        return groupJob;
    }

    public void setGroupJob(String groupJob) {
        this.groupJob = groupJob;
    }

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getMisfire() {
        return misfire;
    }

    public void setMisfire(Integer misfire) {
        this.misfire = misfire;
    }

    public Integer getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(Integer concurrent) {
        this.concurrent = concurrent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
