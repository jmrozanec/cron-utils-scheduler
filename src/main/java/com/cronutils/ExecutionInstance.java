package com.cronutils;

import java.time.ZonedDateTime;

public class ExecutionInstance {
    private Job job;
    private ZonedDateTime time;
    private ExecutionStatus status;

    public ExecutionInstance(Job job, ZonedDateTime time, ExecutionStatus status) {
        this.job = job;
        this.time = time;
        this.status = status;
    }

    public Job getJob() {
        return job;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public ExecutionStatus getStatus() {
        return status;
    }
}
