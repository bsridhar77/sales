package com.demo.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.model.CustomData;
import com.demo.app.model.ResponseData;
import com.demo.app.model.Sales;
import com.demo.app.repository.SalesTemplateImpl;
import com.demo.app.request.SalesRequest;


@RestController
public class SalesService {
	
	@Autowired
	private SalesTemplateImpl salesTemplateImpl;
	
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesService.class);
	
    @RequestMapping(value = "/getsales", method = RequestMethod.POST)
	public List<ResponseData> fetchSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
      	
    	LOGGER.debug("Leaving.");
    	//return salesTemplateImpl.findSales(salesRequest);
    	 return salesTemplateImpl.getDataBetweenDatesUsingCustomAggregationSlice(salesRequest);
    }
   
    @RequestMapping(value = "/sales", method = RequestMethod.POST)
	public Sales createSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Create Sales Object
    	Sales sales=salesTemplateImpl.createSales(salesRequest);
    	LOGGER.debug("Sale Created:" + sales);
    	
    	return sales;
    }

    
    @RequestMapping(value = "/sales", method = RequestMethod.PUT)
	public void updateSales(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Update Sales Object with Request Object
    	salesTemplateImpl.updateSales(salesRequest);
    	LOGGER.debug("Leaving.");
    }
    
    
    @RequestMapping(value = "/sales/type", method = RequestMethod.PUT)
	public void updateSalesWithNewType(@RequestBody SalesRequest salesRequest) {
    	LOGGER.debug("Entering...");
    	LOGGER.debug("Received...salesRequest:::" + salesRequest);
    	
    	//Update Sales Object with New Type from Request Object
    	salesTemplateImpl.updateSalesWithNewType(salesRequest);
    	LOGGER.debug("Sale Updated:");
    	LOGGER.debug("Leaving.");
    }
    
   
}