package com.cronutils;

public class ExecutableUnit implements Comparable<ExecutableUnit>{
    private Job job;
    private Trigger trigger;

    public ExecutableUnit(Job job, Trigger trigger) {
        this.job = job;
        this.trigger = trigger;
    }

    public Job getJob() {
        return job;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public int compareTo(ExecutableUnit o) {
        if(trigger.nextExecution().isPresent()){
            if(o.trigger.nextExecution().isPresent()){
                return trigger.nextExecution().get().compareTo(o.trigger.nextExecution().get());
            }else{
                return 1;
            }
        }
        return -1;
    }
}
