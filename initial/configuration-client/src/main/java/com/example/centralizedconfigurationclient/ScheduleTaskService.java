package com.example.centralizedconfigurationclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();
	
	private final Environment env;
	
	public ScheduleTaskService(TaskScheduler scheduler, Environment env) {
		this.scheduler = scheduler;
		this.env = env;
	}
	
	// Added Trigger interface parameter so the client can decide how to schedule the task.
	// Maybe it'll always be a cron job, so I can just keep it as a CronTrigger? We'll see. 
	public void addTaskToScheduler(int id, Runnable task, Trigger trigger) {
		ScheduledFuture<?> scheduledTask = scheduler.schedule(task, trigger);
		jobsMap.put(id, scheduledTask);
		System.out.println("message: " + env.getProperty("message"));
		System.out.println("cron: " + env.getProperty("cron"));
		System.out.println("geobridge.jobCount: " + env.getProperty("geobridge.jobCount"));
	}
	
	public void removeTaskFromScheduler(Integer id) {
		ScheduledFuture<?> scheduledTask = jobsMap.get(id);
		if(scheduledTask != null) {
			scheduledTask.cancel(true);
			jobsMap.remove(id);
		}
	}
	
	public int getJobCount() {
		return this.jobsMap.size();
	}
	
	public boolean hasJobId(Integer id) {
		return jobsMap.containsKey(id);
	}
	
	public void cancelAllTasks() {
		for (Integer id : this.jobsMap.keySet()) {
			this.removeTaskFromScheduler(id);
		}
	}
	
	// I think a different entity should be listening to the refresh action.
	// This service should just implement whatever needs to happen when a refresh occurs. 
	//    @EventListener(RefreshScopeRefreshedEvent.class)
	//    public void onRefresh(RefreshScopeRefreshedEvent event) {
	//		System.out.println("I HAVE DETECTED A REFRESH EVENT SO I WILL REFRESH ALL OF MY JOBS.");
	//    }
	
}