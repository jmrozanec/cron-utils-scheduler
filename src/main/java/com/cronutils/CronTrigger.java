package com.cronutils;

import com.cronutils.model.Cron;
import com.cronutils.model.time.ExecutionTime;
import com.google.common.base.Optional;
import org.threeten.bp.ZonedDateTime;

public class CronTrigger implements Trigger {
    private Cron cron;

    public CronTrigger(Cron cron) {
        this.cron = cron;
    }

    public Optional<ZonedDateTime> nextExecution(){
        return ExecutionTime.forCron(cron).nextExecution(ZonedDateTime.now());
    }
}
