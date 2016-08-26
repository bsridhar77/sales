package com.demo.app.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.app.model.SalesKey;

@Component
public class SalesHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesHelper.class);
	 public SalesKey getSalesKeyFromRequest(String hostname,Date date){
	    	LOGGER.info("Constructing SalesKey Object:");
	    	LOGGER.info("Received:hostname::date:::" + hostname +":::" + date);
	    	SalesKey salesKey=new SalesKey();
	    	salesKey.setHostName(hostname);
	    	salesKey.setTimestamp(date);
	    	LOGGER.info("Constructed SalesKey Object:" + salesKey);
	    	return salesKey;
	    }
}
