//package com.example.centralizedconfigurationclient;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.config.environment.Environment;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.Trigger;
//import org.springframework.scheduling.TriggerContext;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Configuration
//@EnableScheduling
//@Getter
//@Setter
//public class SchedulingConfiguration implements SchedulingConfigurer {
//    private static final Logger log = LoggerFactory.getLogger(GeoBridgeJob.class);
//    
//    @Autowired
//    GeoBridgeJob job;
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//    	System.out.println("~~~~~~~~~~~~~ " + job.getJob_count());
//        for (int i = 0; i < Integer.valueOf(job.getJob_count()); i++) {
//            Runnable runnable = () -> System.out.println("Trigger task executed at " + new Date() + " jobCount: " + job.getJob_count());
//            Trigger trigger = new Trigger() {
//                @Override
//                public Date nextExecutionTime(TriggerContext triggerContext) {
//                    CronTrigger crontrigger = new CronTrigger("0/5 * * * * *");
//                    return crontrigger.nextExecutionTime(triggerContext);
//                }
//            };
//            taskRegistrar.addTriggerTask(runnable, trigger);
//        }
//    }
//}