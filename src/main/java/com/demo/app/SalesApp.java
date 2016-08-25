package com.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;






@SpringBootApplication
public class SalesApp {
	
	
	

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesApp.class);
    public static void main(String[] args) {
    	
        SpringApplication.run(SalesApp.class, args);
    }
  

}