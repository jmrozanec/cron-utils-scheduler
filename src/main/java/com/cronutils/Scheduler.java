package com.cronutils;

import com.google.common.base.Optional;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.threeten.bp.ZonedDateTime;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class Scheduler {
    private BlockingQueue<Runnable> running = Queues.newArrayBlockingQueue(1000);
    private Executor executor = new ThreadPoolExecutor(50,100,10000, TimeUnit.MINUTES, running);

    private ScheduledExecutorService poller;
    private Set<String> executingIDs = Sets.newConcurrentHashSet();
    private Queue<ExecutableUnit> waiting = MinMaxPriorityQueue.maximumSize(1000).create();
    private Queue<ExecutableUnit> executing = MinMaxPriorityQueue.maximumSize(1000).create();
    private Queue<ExecutableUnit> succeeded = MinMaxPriorityQueue.maximumSize(1000).create();
    private Queue<ExecutableUnit> failed = MinMaxPriorityQueue.maximumSize(1000).create();

    public Scheduler(){
        poller = Executors.newScheduledThreadPool(10);
        poller.scheduleAtFixedRate(() -> {
            ExecutableUnit unit = waiting.poll();
            if(unit!=null){
                Optional<ZonedDateTime> next = unit.getTrigger().nextExecution();
                if(next.isPresent() && isNextSecond(next.get())){
                    //TODO verify date of last execution is not current
                    //TODO group executing add/remove into a method
                    //TODO create ExecutionInstance and manage status there
                    if(!executingIDs.contains(unit.getJob().getId())){
                        executing.add(unit);
                        executor.execute(unit.getJob());
                        executingIDs.add(unit.getJob().getId());
                    }
                }else{
                    if(next.isPresent()){
                        waiting.add(unit);
                    }
                }
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    private boolean isNextSecond(ZonedDateTime next){
        return true;
    }

    public void schedule(ExecutableUnit executableUnit){
        executableUnit.getJob().addStatusHook(executionStatus -> {
            executing.remove(executableUnit);
            executingIDs.remove(executableUnit.getJob().getId());
            switch (executionStatus){
                case FAILURE:
                    failed.add(executableUnit);
                    break;
                case SUCCESS:
                    succeeded.add(executableUnit);
                    break;
            }
            if(executableUnit.getTrigger().nextExecution().isPresent()){
                waiting.add(executableUnit);
            }
            return null;
        });
        waiting.add(executableUnit);
    }
}
