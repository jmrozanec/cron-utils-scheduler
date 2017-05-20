# cron-utils-scheduler
A Java job scheduler based on cron-utils library. Supports multiple cron formats - even custom ones! 

- priority WAITING queue: jobs sorted by date: more recent first. Wait until pulled to EXECUTING queue.
- priority EXECUTING queue: jobs sorted by date: more recent first. A pool of executors retrieves them and executes ASAP.
- watcher thread: every N ms (where N ms <1s), check next job(s) to be executed and assign them to EXECUTING queue
- executor: executes assigned jobs. Notifies execution end state: SUCCEEDED, FAILED
- SUCCEEDED queue: holds last successfuly executed jobs
- FAILED queue: holds last FAILED jobs

Consider:
- ability to schedule Runnables
- ability to schedule jobs that must hold no state (except for serializable parameters) and should be created by reflection (support distributed execution in a future)
