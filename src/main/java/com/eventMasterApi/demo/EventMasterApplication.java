package com.eventMasterApi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.eventMasterApi.demo")
public class EventMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventMasterApplication.class, args);
	}

}
