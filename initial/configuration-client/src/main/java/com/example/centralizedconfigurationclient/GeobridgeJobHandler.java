package com.example.centralizedconfigurationclient;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GeobridgeJobHandler {

	private final Environment env;

    @Autowired
    ScheduleTaskService taskService;
    
	public GeobridgeJobHandler(Environment env) {
		this.env = env;
	}
	
    @PostConstruct
	void init() {
		Integer jobCount = Integer.valueOf(env.getProperty("geobridge.jobCount"));
		System.out.println("Initializing " + jobCount + " Geobridge jobs.");
		for (int i = 0; i < jobCount; i++) {
			String cron = env.getProperty("geobridge.job." + i + ".cron");
			String keybridgeUrl = env.getProperty("geobridge.job." + i + ".keybridgeUrl");
			String password = env.getProperty("geobridge.job." + i + ".password");
			
			System.out.println("cron: " + cron);
			System.out.println("keybridgeUrl: " + keybridgeUrl);
			System.out.println("password: " + password);
			GeobridgeJob geobridgeJob = new GeobridgeJob(cron, keybridgeUrl, password);
			this.taskService.addTaskToScheduler(1, geobridgeJob, new CronTrigger(cron));
		}
	}
}
