package com.example.centralizedconfigurationclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class SpringConfiguration {

    @Autowired
    Environment env;

    //The implementation of TaskService in ScheduleTaskService will be a ThreadPoolTaskScheduler.
    @Bean
    public TaskScheduler taskScheduler() {
        //You can also specify the pool size here. I'm just going to leave it to the default of 1 for now. 
        return new ThreadPoolTaskScheduler();
    }
}