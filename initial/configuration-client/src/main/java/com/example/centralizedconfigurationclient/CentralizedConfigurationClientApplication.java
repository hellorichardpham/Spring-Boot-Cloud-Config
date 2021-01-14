/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.centralizedconfigurationclient;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication
public class CentralizedConfigurationClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralizedConfigurationClientApplication.class, args);
    }
}

@RefreshScope
@RestController
@EnableScheduling
class MessageRestController {

    @Autowired
    ScheduleTaskService taskService;

//    @Value("${message:This is a default value for message. If you see this value, that means the the config value was not loaded in properly from Git. }")
//    private String message;

    @Value("${job_count:This is a default value for jobCount. If you see this value, that means the the config value was not loaded in properly from Git. }")
    private String job_count;

    @Value("${job_0_cron:This is a default value for job_0_cron. If you see this value, that means the the config value was not loaded in properly from Git. }")
    private String job_0_cron;
    @Value("${job_0_property:This is a default value for job_0_property. If you see this value, that means the the config value was not loaded in properly from Git. }")
    private String job_0_property;

//    @RequestMapping("/message")
//    String getMessage() {
//        return this.message;
//    }

    @RequestMapping("/jobcount")
    String getJobCount() {
        return this.job_count;
    }

    @RequestMapping("/job0")
    String getJob0() {
        return this.job_0_cron + " " + this.job_0_property;
    }
    
}


































