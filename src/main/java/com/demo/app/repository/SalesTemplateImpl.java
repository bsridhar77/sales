package com.demo.app.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.demo.app.model.ResponseData;
import com.demo.app.model.Sales;
import com.demo.app.model.SalesData;
import com.demo.app.model.SalesHour;
import com.demo.app.request.SalesRequest;
import com.demo.app.util.SalesHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
@Component
public class SalesTemplateImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesTemplateImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SalesHelper salesHelper;
	
	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	SalesHourRepository salesHourRepository;
	public void saveSales(Sales sales){
		salesRepository.save(sales);
	}
	
 private Aggregation getAggregation(SalesRequest salesRequest){
	 

		Date fromDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getFromTime());
		Date toDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getToTime());
		
		Criteria customDateTypeCriteria=Criteria.where("salesData.type").is(salesRequest.getType());
		Criteria timestampCriteria=Criteria.where("timestamp").gte(fromDateTime).lte(toDateTime);
		Criteria hostNameCriteria=Criteria.where("hostName").is(salesRequest.getHostname());
		
		Criteria dateCriteria=new Criteria();
		
		dateCriteria.andOperator(timestampCriteria,hostNameCriteria,dateCriteria);
		
		
		Aggregation aggregation = newAggregation(
				 match(dateCriteria),
				 unwind("$salesData"),
				 match(customDateTypeCriteria),
				 spliceArray("salesData.volume",0,1)
	    );
		return aggregation;
 }
	

	public List<ResponseData> getSalesData(SalesRequest salesRequest){
		
		
		Aggregation aggregation=getAggregation(salesRequest);
		
		AggregationResults<ResponseData> groupResults
			= mongoTemplate.aggregate(aggregation, Sales.class, ResponseData.class);
		List<ResponseData> result = groupResults.getMappedResults();
		
		return result;
		
	}
	
public List<ResponseData> getSalesHourData(SalesRequest salesRequest){
		
		
		Aggregation aggregation=getAggregation(salesRequest);
			
		
		AggregationResults<ResponseData> groupResults
			= mongoTemplate.aggregate(aggregation, SalesHour.class, ResponseData.class);
		List<ResponseData> result = groupResults.getMappedResults();
		
		return result;
		
	}
  private CustomOperation spliceArray(String fieldToSlice,int startPos,int count){
	  
	  return new CustomOperation(
              new BasicDBObject("$project",
                  new BasicDBObject("_id",0)
                  		  .append("timestamp",1)
                          .append("mydata" , new BasicDBObject("$slice",Arrays.asList("$" + fieldToSlice,startPos,count))) 
                      ));
  }

	public Sales findSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		    	
		Sales sales=salesRepository.findBySalesKey(salesRequest.getDate(),salesRequest.getHostname());
		LOGGER.debug("Leaving");
		return sales;
	}
	
	private Query getQueryObjectFromRequest(SalesRequest salesRequest){
		
		Date fromDateTime=salesHelper.getDateTime(salesRequest.getSalesHourTimestamp(), salesRequest.getTime());
		Query query = new Query(
				Criteria.where("timestamp").is(fromDateTime)
				.andOperator(
								Criteria.where("salesData.type").is(salesRequest.getType()),
								Criteria.where("hostName").is(salesRequest.getHostname())
							)
				);
		return query;
	}

	public boolean isTypeExists(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		boolean status=false;
				
		//Construct Query Object from Request object
		Query query=getQueryObjectFromRequest(salesRequest);
		
		//Find Sales Object using the query object
		Sales sales=mongoTemplate.findOne(query, Sales.class);
		
		
		if(null!=sales){
			status=true;
		}
		
		LOGGER.debug("Leaving");
		return status;
	}
	
	public void updateSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		Query query=getQueryObjectFromRequest(salesRequest);
		
		
		//Construct Update Object using Request Object
		Update update=new Update();
		update.set("totalAmount", salesRequest.getTotalAmount());
		update.push("salesData.$.volume",salesRequest.getVolume());
			
		//Find and Update Document 
		WriteResult result=mongoTemplate.updateFirst(query,update,Sales.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
	}
	
	public void updateSalesWithNewType(SalesRequest salesRequest){
		
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
    	
		//Check if type already exists
		if(isTypeExists(salesRequest)){
			LOGGER.debug("Type Exists , proceeding to update it now");
			updateSales(salesRequest);
			return;
		}
		
		
		SalesData salesData=new SalesData(
				salesRequest.getType(),
				new String[]{salesRequest.getVolume()}
			 );
		
		//Construct Query Object
		
		Query query = new Query(
				Criteria.where("timestamp").is(salesRequest.getDate())
				.andOperator(
								Criteria.where("salesData.type").is(salesRequest.getType()),
								Criteria.where("hostName").is(salesRequest.getHostname())
							)
				);
		//Construct Update Object using Request Object
		Update update=new Update();
		update.push("salesData",salesData);
		
		//Find and Update Document
		WriteResult result=mongoTemplate.upsert(query,update,Sales.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
	}
	
	
	public Sales createSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		//Construct Sales object from Request object and SalesKey Object
		Sales sales=new Sales();
		sales.setId(salesRequest.getSalesId());
		sales.setTotalAmount(salesRequest.getTotalAmount());
		sales.setHostName(salesRequest.getHostname());
		sales.setTimestamp(salesHelper.getDateWithoutSeconds(new Date()));
		
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




	public SalesHour createSalesHour(SalesRequest salesRequest) {
		LOGGER.debug("Entering");
		
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		//Construct Sales object from Request object and SalesKey Object
		SalesHour salesHour=new SalesHour();
		salesHour.setId(salesRequest.getSalesId());
		salesHour.setTotalAmount(salesRequest.getTotalAmount());
		salesHour.setHostName(salesRequest.getHostname());
		salesHour.setTimestamp(salesHelper.getDateWithoutMinutesSeconds(new Date()));
		
		//Construct SalesData object from Request object
		SalesData salesData=new SalesData(
											salesRequest.getType(),
											new String[]{salesRequest.getVolume()}
										 );
		
		//Construct SalesData object Collection using SalesData Object
		List<SalesData> salesDataList=new ArrayList<SalesData>();
		salesDataList.add(salesData);
		salesHour.setSalesData(salesDataList);
		
		
		
		
		//Save the sales Object and return the Saved Sales Object
		SalesHour newSalesHour=salesHourRepository.save(salesHour);
		
		LOGGER.debug("Leaving");
		return newSalesHour;
	}

	public void updateSalesHour(SalesRequest salesRequest) {
	LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		Query query=getQueryObjectFromRequest(salesRequest);
		
		
		//Construct Update Object using Request Object
		Update update=new Update();
		update.set("totalAmount", salesRequest.getTotalAmount());
		update.push("salesData.$.volume",salesRequest.getVolume());
			
		//Find and Update Document 
		WriteResult result=mongoTemplate.updateFirst(query,update,SalesHour.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
		
	}


}