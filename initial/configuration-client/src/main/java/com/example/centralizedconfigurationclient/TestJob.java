package com.example.centralizedconfigurationclient;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TestJob {
//	private static final Logger log = LoggerFactory.getLogger(TestJob.class);
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//	
//	@Value("${fixedRateValue:This is a default value for job_0_cron. If you see this value, that means the the config value was not loaded in properly from Git. }")
//	private String fixedRateValue;
//	  
//	@Scheduled(cron = fixedRateValue)
//	public void runJob() {
//		log.info("The time is now {}", dateFormat.format(new Date()));
//	}
}
