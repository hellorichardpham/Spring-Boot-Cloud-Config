package com.example.centralizedconfigurationclient;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GeobridgeJobHandler {
	
    @Autowired
    private ScheduleTaskService taskService;
    private final Environment env;
    private final String JOB_PREFIX = "geobridge.job.";
    
	public GeobridgeJobHandler(Environment env) {
		this.env = env;
	}
	
    @PostConstruct
	private void init() {
    	this.initializeJobs();
	}
    
    private void initializeJobs() {
    	Integer jobCount = Integer.valueOf(env.getProperty("geobridge.jobCount"));
		System.out.println("Initializing " + jobCount + " Geobridge jobs.");
		for (int i = 0; i < jobCount; i++) {
			String cron = env.getProperty(JOB_PREFIX + i + ".cron");
			String keybridgeUrl = env.getProperty(JOB_PREFIX + i + ".keybridgeUrl");
			String password = env.getProperty(JOB_PREFIX + i + ".password");
			
			GeobridgeJob geobridgeJob = new GeobridgeJob(cron, keybridgeUrl, password);
			this.taskService.addTaskToScheduler(geobridgeJob, new CronTrigger(cron));
		}
    }
    
    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
		System.out.println("A refresh was detected. Cancelling all running jobs.");
    	this.taskService.cancelAllJobs();
    	System.out.println("I have finished calling my init method.");
    	this.init();
    }

}
