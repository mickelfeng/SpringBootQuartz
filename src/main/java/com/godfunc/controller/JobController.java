package com.godfunc.controller;

import com.godfunc.entity.JobEntity;
import com.godfunc.service.JobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("add")
    public String add(JobEntity job) {
        if (jobService.add(job)) {
            return "OK";
        }
        return "fail";
    }

    @PostMapping("edit")
    public String edit(JobEntity job) {
        if (jobService.edit(job)) {
            return "OK";
        }
        return "fail";
    }

    @PostMapping("remove")
    public String remove(JobEntity job) {
        if (jobService.remove(job)) {
            return "OK";
        }
        return "fail";
    }

}
