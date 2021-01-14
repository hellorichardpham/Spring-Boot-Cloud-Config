package com.example.centralizedconfigurationclient;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@EnableScheduling
public class JobRestController {

    @Autowired
    ScheduleTaskService taskService;
    
    @PostMapping(value = "/job", consumes = "application/json")
    void addJob() { 
    	Runnable runnable = new Runnable() {
	        public void run() {
	        	System.out.println("The time is " + new Date());
           }
		};
        taskService.addTaskToScheduler(1, runnable, new CronTrigger("0/5 * * * * *"));
        System.out.println("Successfully added job with id: " + 1);

    }
    
    @DeleteMapping(value = "/job/{id}")
    void addJob(@PathVariable(value = "id") int jobId) { 
    	taskService.removeTaskFromScheduler(jobId);
    	System.out.println("Successfully removed job with id: " + jobId);
    }
    
}
