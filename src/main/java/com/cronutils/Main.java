package com.cronutils;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        Job job = new Job("Hello world!") {
            @Override
            protected ExecutionStatus execute() {
                System.out.println("Hello world!");
                return ExecutionStatus.SUCCESS;
            }
        };
        Trigger trigger = new CronTrigger(new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ)).parse("* * * ? * * *"));
        scheduler.schedule(new ExecutableUnit(job, trigger));
    }
}
