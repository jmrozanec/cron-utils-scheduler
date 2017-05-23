package com.cronutils;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class Job implements Runnable {
    private String id;
    private ExecutionStatus status = ExecutionStatus.WAITING;
    private List<Function<ExecutionStatus, Void>> statusHooks = Lists.newArrayList();

    protected Job(String id) {
        this.id = id;
    }

    protected abstract ExecutionStatus execute();

    public final void run(){
        status = ExecutionStatus.RUNNING;
        status = execute();
        reportStatus();
    }

    void addStatusHook(Function<ExecutionStatus, Void> hook){
        statusHooks.add(hook);
    }

    private void reportStatus(){
        for(Function<ExecutionStatus, Void> f : statusHooks){
            f.apply(status);
        }
    }

    public String getId() {
        return id;
    }
}
