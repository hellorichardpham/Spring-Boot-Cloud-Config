package com.example.centralizedconfigurationclient;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class GeobridgeJob implements Runnable {
	private String cron;
	private String keyBridgeUrl;
	private String password;
	
	public GeobridgeJob() {
		
	}
	
	public GeobridgeJob(String cron, String keyBridgeUrl, String password) {
		this.cron = cron;
		this.keyBridgeUrl = keyBridgeUrl;
		this.password = password;
	}
	
	@Override
	public void run() {
		System.out.println("cron: " + this.cron + " url: " + keyBridgeUrl + " password: " + password);
	}
	
}
