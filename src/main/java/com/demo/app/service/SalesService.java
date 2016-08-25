package com.demo.app.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesData;
import com.demo.app.model.SalesKey;
import com.demo.app.repository.SalesTemplateImpl;
import com.demo.app.request.SalesRequest;
@RestController
public class SalesService {
	
	@Autowired
	private SalesTemplateImpl salesTemplateImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesService.class);
	
    @RequestMapping(value = "/getsales", method = RequestMethod.POST)
	public Sales fetchSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.info("Entering...");
    	LOGGER.info("Received...salesRequest:::" + salesRequest);
    	
    	SalesKey salesKey=new SalesKey();
    	salesKey.setHostName(salesRequest.getHostname());
    	salesKey.setTimestamp(salesRequest.getDate());
    	LOGGER.info("Leaving.");
    	return salesTemplateImpl.findSales(salesKey);
    }
   
    @RequestMapping(value = "/sales", method = RequestMethod.POST)
	public Sales createSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.info("Entering...");
    	LOGGER.info("Received...salesRequest:::" + salesRequest);
    	
    	SalesKey salesKey=new SalesKey();
    	salesKey.setHostName(salesRequest.getHostname());
    	salesKey.setTimestamp(salesRequest.getDate());
    	
    	SalesData salesData1=new SalesData();
    	salesData1.setType(salesRequest.getType());
    	salesData1.setVolume(new String[]{"1","2","3","4","5"});
    	
    	
    	
    	Sales sales=salesTemplateImpl.createSales(salesKey,salesData1, salesRequest.getTotalAmount());
    	LOGGER.info("Sale Created:" + sales);
    	return sales;
    }

    
    @RequestMapping(value = "/sales", method = RequestMethod.PUT)
	public void updateSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.info("Entering...");
    	LOGGER.info("Received...salesRequest:::" + salesRequest);
    	
    	SalesKey salesKey=new SalesKey();
    	salesKey.setHostName(salesRequest.getHostname());
    	salesKey.setTimestamp(salesRequest.getDate());
    	LOGGER.info("Leaving.");
    	salesTemplateImpl.updateSales(salesKey, salesRequest);
    }
    
    
    @RequestMapping(value = "/sales/type", method = RequestMethod.PUT)
	public void updateSalesWithNewType(@RequestBody SalesRequest salesRequest) {
    	LOGGER.info("Entering...");
    	LOGGER.info("Received...salesRequest:::" + salesRequest);
    	
    	SalesKey salesKey=new SalesKey();
    	salesKey.setHostName(salesRequest.getHostname());
    	salesKey.setTimestamp(salesRequest.getDate());
    	LOGGER.info("Leaving.");
    	salesTemplateImpl.updateSalesWithNewType(salesKey, salesRequest);
    }
}
