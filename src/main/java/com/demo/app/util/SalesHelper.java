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
	 
	 public Date getDateWithoutSeconds(Date date){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set(Calendar.SECOND, 0);
	     calendar.set(Calendar.MILLISECOND, 0);
	     return calendar.getTime();
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
	        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinute[0]));
	        calendar.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
	      
	        System.out.println("DATE-Time= " + calendar.getTime());
		return calendar.getTime();
	}
	
	public static void main(String[] arg){
		/*Calendar calendar = Calendar.getInstance();
		calendar.set*/
		
		
		Calendar cal = Calendar.getInstance();
	      // You cannot use Date class to extract individual Date fields
	      int year = cal.get(Calendar.YEAR);
	      int month = cal.get(Calendar.MONTH);      // 0 to 11
	      int day = cal.get(Calendar.DAY_OF_MONTH);
	      int hour = cal.get(Calendar.HOUR_OF_DAY);
	      int minute = cal.get(Calendar.MINUTE);
	      int second = cal.get(Calendar.SECOND);
	   
	      System.out.printf("Now is %4d/%02d/%02d %02d:%02d:%02d\n",  // Pad with zero
	          year, month+1, day, hour, minute, second);
		new SalesHelper().getDateTime(new Date(),"03:00");
	}
}
