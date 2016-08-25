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
		return salesRepository.filterBySalesKey(salesKey);
	}
	public void updateSales(SalesKey salesKey,SalesRequest salesRequest){
		Query query = new Query(Criteria.where("_id").is(salesKey).andOperator(Criteria.where("salesData.type").is(salesRequest.getType())));
		Update update=new Update();
		update.set("totalAmount", salesRequest.getTotalAmount());
		update.push("salesData.$.volume",salesRequest.getVolume());
		
		WriteResult result=mongoTemplate.updateFirst(query,update,Sales.class);
		LOGGER.info("WriteResult:" + result);
	}
	public Sales createSales(SalesKey salesKey,SalesData salesData,String totalAmount){
		Sales sales=new Sales();
		sales.setSalesKey(salesKey);
		sales.setTotalAmount(totalAmount);
		List<SalesData> salesDataList=new ArrayList();
		salesDataList.add(salesData);
		sales.setSalesData(salesDataList);
		return salesRepository.save(sales);
		
	}

}
