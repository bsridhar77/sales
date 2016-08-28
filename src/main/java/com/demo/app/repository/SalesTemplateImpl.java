package com.demo.app.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.demo.app.model.CustomData;
import com.demo.app.model.ResponseData;
import com.demo.app.model.Sales;
import com.demo.app.model.SalesData;
import com.demo.app.model.SalesKey;
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
	public void saveSales(Sales sales){
		salesRepository.save(sales);
	}
	
	public List<ResponseData> getDataBetweenDatesUsingAggregation(SalesRequest salesRequest){
		
		Date fromDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getFromTime());
		//Date toDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getToTime());
		
		Criteria customDateTypeCriteria=Criteria.where("customData.type").is(salesRequest.getType());
		//Criteria timestampCriteria=Criteria.where("_id.timestamp").is(fromDateTime);
		Criteria hostNameCriteria=Criteria.where("_id.hostName").is(salesRequest.getHostname());
		Criteria dateCriteria=new Criteria();
		dateCriteria.andOperator(hostNameCriteria,dateCriteria);
		
		Aggregation agg = newAggregation(Sales.class, 
				 match(dateCriteria),
				 unwind("$salesData"),
				 match(customDateTypeCriteria),
				 project("customData.mydata.8")
			);

		
	
		//Convert the aggregation result into a List
		AggregationResults<ResponseData> groupResults
			= mongoTemplate.aggregate(agg, Sales.class, ResponseData.class);
		List<ResponseData> result = groupResults.getMappedResults();
		return result;
		
	}
	
	

	public List<ResponseData> getDataBetweenDatesUsingCustomAggregationSlice(SalesRequest salesRequest){
		
		Date fromDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getFromTime());
		//Date toDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getToTime());
		
		Criteria customDateTypeCriteria=Criteria.where("salesData.type").is(salesRequest.getType());
		Criteria timestampCriteria=Criteria.where("_id.timestamp").gte(fromDateTime);
		Criteria hostNameCriteria=Criteria.where("_id.hostName").is(salesRequest.getHostname());
		
		Criteria dateCriteria=new Criteria();
		
		dateCriteria.andOperator(timestampCriteria,hostNameCriteria,dateCriteria);
		
		
		
		
		Aggregation aggregation = newAggregation(
				 match(dateCriteria),
				 unwind("$salesData"),
				 match(customDateTypeCriteria),
				 spliceArray("salesData.volume",0,1)
	    );
			
		//Convert the aggregation result into a List
		AggregationResults<ResponseData> groupResults
			= mongoTemplate.aggregate(aggregation, Sales.class, ResponseData.class);
		List<ResponseData> result = groupResults.getMappedResults();
		return result;
		
	}
  private CustomOperation spliceArray(String fieldToSlice,int startPos,int count){
	  
	  return new CustomOperation(
              new BasicDBObject("$project",
                  new BasicDBObject("_id",0)
                          .append("mydata" , new BasicDBObject("$slice",Arrays.asList(
                                  "$" + fieldToSlice,
                                  startPos,count
                          )))   ));
  }
	public List<ResponseData> getDataBetweenDatesUsingAggregationSlice(SalesRequest salesRequest){
		
		Date fromDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getFromTime());
		//Date toDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getToTime());
		
		Criteria customDateTypeCriteria=Criteria.where("customData.type").is(salesRequest.getType());
		//Criteria timestampCriteria=Criteria.where("_id.timestamp").is(fromDateTime);
		Criteria hostNameCriteria=Criteria.where("_id.hostName").is(salesRequest.getHostname());
		Criteria dateCriteria=new Criteria();
		dateCriteria.andOperator(hostNameCriteria,dateCriteria);
		
		Aggregation agg = newAggregation(Sales.class, 
				 match(dateCriteria),
				 unwind("$salesData"),
				 match(customDateTypeCriteria)//,
				// project().and("$salesData.volume").slice(10, 5).as("renamed")
				);

		
		//ProjectionOperation operation = Aggregation.project().and("field").slice(10).as("renamed");
		//Convert the aggregation result into a List
		AggregationResults<ResponseData> groupResults
			= mongoTemplate.aggregate(agg, Sales.class, ResponseData.class);
		List<ResponseData> result = groupResults.getMappedResults();
		return result;
		
	}
	
	public List<Sales> getDataBetweenDates(SalesRequest salesRequest){
		Date fromDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getFromTime());
		Date toDateTime=salesHelper.getDateTime(salesRequest.getDate(), salesRequest.getToTime());
		
		
		
		//Criteria timestampCriteria=Criteria.where("_id.timestamp").gte(fromDateTime).lte(toDateTime);
		Criteria customDateTypeCriteria=Criteria.where("customData.type").is(salesRequest.getType());
		Criteria hostNameCriteria=Criteria.where("_id.hostName").is(salesRequest.getHostname());
		Criteria dateCriteria=new Criteria();
		dateCriteria.andOperator(customDateTypeCriteria,hostNameCriteria,dateCriteria);
		Query query=new Query();
		query.addCriteria(dateCriteria);
				      
		
		query.fields().include("customData.mydata." + salesRequest.getHour());
		//query.fields().include("customData.mydata.9");
		query.fields().exclude("_id");
		//Find Sales Object using the query object
		List<Sales> salesList=mongoTemplate.find(query, Sales.class);
	
		return salesList;
		
	}
	public Sales findSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
    	//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate(),salesRequest.getTime());
		Sales sales=salesRepository.filterBySalesKey(salesKey);
		LOGGER.debug("Leaving");
		return sales;
	}
	
	private Query getQueryObjectFromRequest(SalesKey salesKey,SalesRequest salesRequest){
		Query query = new Query(
				Criteria.where("_id").is(salesKey)
				.andOperator(
								Criteria.where("salesData.type").is(salesRequest.getType()),
								Criteria.where("customData.type").is(salesRequest.getType())
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
	
	public void updateSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		
		//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate(),salesRequest.getTime());
		//Construct Query Object from Request object
		Query query=getQueryObjectFromRequest(salesKey,salesRequest);
		
		
		//Construct Update Object using Request Object
		Update update=new Update();
		update.set("totalAmount", salesRequest.getTotalAmount());
		update.push("salesData.$.volume",salesRequest.getVolume());
		update.set("customData.$.mydata." + salesRequest.getHour() ,salesRequest.getVolume());
		
		//Find and Update Document 
		WriteResult result=mongoTemplate.updateFirst(query,update,Sales.class);
		
		LOGGER.debug("WriteResult:" + result);
		LOGGER.debug("Leaving");
	}
	
	public void updateSalesWithNewType(SalesRequest salesRequest){
		
		LOGGER.debug("Entering");
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate(),salesRequest.getTime());
    	
		//Check if type already exists
		if(isTypeExists(salesKey,salesRequest)){
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
	
	
	public Sales createSales(SalesRequest salesRequest){
		LOGGER.debug("Entering");
		
		
		LOGGER.debug("Received SalesRequest obj:" + salesRequest);
		
		//Construct SalesKey Object
    	SalesKey salesKey=salesHelper.getSalesKeyFromRequest(salesRequest.getHostname(),salesRequest.getDate(),salesRequest.getTime());
		
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
		
		CustomData customData=new CustomData();
		customData.setMydata(getMyDataMap());
		customData.setType(salesRequest.getType());
		List<CustomData> customDataList=new ArrayList<CustomData>();
		customDataList.add(customData);
		sales.setCustomData(customDataList);
		//Save the sales Object and return the Saved Sales Object
		Sales newSales=salesRepository.save(sales);
		
		LOGGER.debug("Leaving");
		return newSales;
		
	}

	private Map<String, String> getMyDataMap() {
		Map<String,String> mintuteMap=new HashMap<String,String>();
		for(int i=0;i<60;i++){
			mintuteMap.put(i+"", "");
		}
		return mintuteMap;
	}

}