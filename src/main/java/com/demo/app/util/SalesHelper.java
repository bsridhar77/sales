package com.demo.app.util;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.app.model.SalesKey;

@Component
public class SalesHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesHelper.class);
	 public SalesKey getSalesKeyFromRequest(String hostname,Date date, String time){
		 	Date dateTime=getDateTime(date,time);
	    	LOGGER.info("Constructing SalesKey Object:");
	    	LOGGER.info("Received:hostname::date:::" + hostname +":::" + date);
	    	SalesKey salesKey=new SalesKey();
	    	salesKey.setHostName(hostname);
	    	salesKey.setTimestamp(dateTime);
	    	LOGGER.info("Constructed SalesKey Object:" + salesKey);
	    	return salesKey;
	    }
	public Date getDateTime(Date date, String time) {
		 Calendar calendar = Calendar.getInstance();
	        System.out.println("DATE = " + date);
	        System.out.println("time = " + time);
	       StringTokenizer strToken=new StringTokenizer(time,":");
	       String hourMinute[]=new String[2];
	       int i=0;
	       if(null!=strToken){
	    	   while(strToken.hasMoreElements()){
	    		hourMinute[i]=(String) strToken.nextElement();   
	    		i++;
	    	   }
	       }
	        calendar.setTime(date);
	        calendar.set(Calendar.HOUR, Integer.parseInt(hourMinute[0]));
	        calendar.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
	      
	        System.out.println("DATE-Time= " + calendar.getTime());
		return calendar.getTime();
	}
}
