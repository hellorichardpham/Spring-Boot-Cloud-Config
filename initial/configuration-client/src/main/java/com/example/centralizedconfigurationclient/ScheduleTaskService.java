package com.example.centralizedconfigurationclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskService {

	// Task Scheduler is an interface. This is injected using a Bean defined in SpringConfiguration.
	// The interface is implemented with ThreadPoolTaskScheduler. 
	private TaskScheduler scheduler;
	
	// A map for keeping track of scheduled tasks
	// In order to cancel tasks, we need to keep track of which jobs are running in case we want to cancel it.
	// There is no "Cancel All" function using the scheduler.
	private List<ScheduledFuture<?>> runningJobs = new ArrayList<ScheduledFuture<?>>();
	
	ScheduleTaskService(TaskScheduler scheduler, Environment env) {
		this.scheduler = scheduler;
	}
	
	// Added Trigger interface parameter so the client can decide how to schedule the task.
	// Maybe it'll always be a cron job, so I can just keep it as a CronTrigger? We'll see. 
	public void addTaskToScheduler(Runnable task, Trigger trigger) {
		ScheduledFuture<?> scheduledTask = scheduler.schedule(task, trigger);
		runningJobs.add(scheduledTask);
	}
	
	public int getJobCount() {
		return this.runningJobs.size();
	}
	
	public void cancelAllJobs() {
		for (ScheduledFuture<?> job : runningJobs) {
			job.cancel(true);
		}
		runningJobs.clear(); 
	}
	
	// I think a different entity should be listening to the refresh action.
	// This service should just implement whatever needs to happen when a refresh occurs. 
	//    @EventListener(RefreshScopeRefreshedEvent.class)
	//    public void onRefresh(RefreshScopeRefreshedEvent event) {
	//		System.out.println("I HAVE DETECTED A REFRESH EVENT SO I WILL REFRESH ALL OF MY JOBS.");
	//    }
	
}