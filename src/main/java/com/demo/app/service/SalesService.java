package com.demo.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesKey;
import com.demo.app.repository.SalesTemplateImpl;
import com.demo.app.request.SalesRequest;
import com.demo.app.util.SalesHelper;
@RestController
public class SalesService {
	
	@Autowired
	private SalesTemplateImpl salesTemplateImpl;
	
	@Autowired
	private SalesHelper salesHelper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesService.class);
	
    @RequestMapping(value = "/getsales", method = RequestMethod.POST)
	public Sales fetchSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	SalesKey salesKey=new SalesKey();
    	salesKey.setHostName(salesRequest.getHostname());
    	salesKey.setTimestamp(salesRequest.getDate());
    	LOGGER.debug("Leaving.");
    	return salesTemplateImpl.findSales(salesKey);
    }
   
    @RequestMapping(value = "/sales", method = RequestMethod.POST)
	public Sales createSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate());
    	
    	//Create Sales Object
    	Sales sales=salesTemplateImpl.createSales(salesKey,salesRequest);
    	LOGGER.debug("Sale Created:" + sales);
    	
    	return sales;
    }

    
    @RequestMapping(value = "/sales", method = RequestMethod.PUT)
	public void updateSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate());
    	
    	//Update Sales Object with Request Object
    	salesTemplateImpl.updateSales(salesKey, salesRequest);
    	LOGGER.debug("Leaving.");
    }
    
    
    @RequestMapping(value = "/sales/type", method = RequestMethod.PUT)
	public void updateSalesWithNewType(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate());
    	
    	//Update Sales Object with New Type from Request Object
    	salesTemplateImpl.updateSalesWithNewType(salesKey, salesRequest);
    	LOGGER.debug("Sale Updated:");
    	LOGGER.debug("Leaving.");
    }
    
   
}
