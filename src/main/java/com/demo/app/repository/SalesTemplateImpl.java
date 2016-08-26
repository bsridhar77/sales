package com.demo.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesData;
import com.demo.app.model.SalesKey;
import com.demo.app.request.SalesRequest;
import com.mongodb.WriteResult;
@Component
public class SalesTemplateImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesTemplateImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	SalesRepository salesRepository;
	public void saveSales(Sales sales){
		salesRepository.save(sales);
	}
	
	public Sales findSales(SalesKey salesKey){
		LOGGER.debug("Entering");
		LOGGER.debug("Received SalesKey obj:" + salesKey);
		Sales sales=salesRepository.filterBySalesKey(salesKey);
		LOGGER.debug("Leaving");
		return sales;
	}
	
	private Query getQueryObjectFromRequest(SalesKey salesKey,SalesRequest salesRequest){
		Query query = new Query(
				Criteria.where("_id").is(salesKey)
				.andOperator(
								Criteria.where("salesData.type").is(salesRequest.getType())
							)
				);
		return query;
	}

	public boolean isTypeExists(SalesKey salesKey,SalesRequest salesRequest){
		LOGGER.debug("Entering");
		LOGGER.debug("Received SalesKey obj:" + salesKey);
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		boolean status=false;
				
		//Construct Query Object from Request object
		Query query=getQueryObjectFromRequest(salesKey,salesRequest);
		
		//Find Sales Object using the query object
		Sales sales=mongoTemplate.findOne(query, Sales.class);
		
		
		if(null!=sales){
			status=true;
		}
		
		LOGGER.debug("Leaving");
		return status;
	}
	
	public void updateSales(SalesKey salesKey,SalesRequest salesRequest){
		LOGGER.debug("Entering");
		LOGGER.debug("Received SalesKey obj:" + salesKey);
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		//Construct Query Object from Request object
		Query query=getQueryObjectFromRequest(salesKey,salesRequest);
		
		
		//Construct Update Object using Request Object
		Update update=new Update();
		update.set("totalAmount", salesRequest.getTotalAmount());
		update.push("salesData.$.volume",salesRequest.getVolume());
		
		
		//Find and Update Document 
		WriteResult result=mongoTemplate.updateFirst(query,update,Sales.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
	}
	
	public void updateSalesWithNewType(SalesKey salesKey,SalesRequest salesRequest){
		
		LOGGER.debug("Entering");
		LOGGER.debug("Received SalesKey obj:" + salesKey);
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		//Check if type already exists
		if(isTypeExists(salesKey,salesRequest)){
			LOGGER.debug("Type Exists , proceeding to update it now");
			updateSales(salesKey,salesRequest);
			return;
		}
		
		
		SalesData salesData=new SalesData(
				salesRequest.getType(),
				new String[]{salesRequest.getVolume()}
			 );
		
		//Construct Query Object
		Query query = new Query(
									Criteria.where("_id").is(salesKey)
							   );
		
		//Construct Update Object using Request Object
		Update update=new Update();
		update.push("salesData",salesData);
		
		//Find and Update Document
		WriteResult result=mongoTemplate.upsert(query,update,Sales.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
	}
	
	
	public Sales createSales(SalesKey salesKey,SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesKey obj:" + salesKey);
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		//Construct Sales object from Request object and SalesKey Object
		Sales sales=new Sales();
		sales.setSalesKey(salesKey);
		sales.setTotalAmount(salesRequest.getTotalAmount());
		
		//Construct SalesData object from Request object
		SalesData salesData=new SalesData(
											salesRequest.getType(),
											new String[]{salesRequest.getVolume()}
										 );
		
		//Construct SalesData object Collection using SalesData Object
		List<SalesData> salesDataList=new ArrayList<SalesData>();
		salesDataList.add(salesData);
		sales.setSalesData(salesDataList);
		
		
		//Save the sales Object and return the Saved Sales Object
		Sales newSales=salesRepository.save(sales);
		
		LOGGER.debug("Leaving");
		return newSales;
		
	}

}
