package com.demo.app.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesKey;
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
	public void updateSales(SalesKey salesKey,String totalAmount){
		Query query = new Query(Criteria.where("_id").is(salesKey));
		WriteResult result=mongoTemplate.updateFirst(query,Update.update("totalAmount", totalAmount),Sales.class);
		LOGGER.info("WriteResult:" + result);
	}
	public Sales createSales(SalesKey salesKey,String totalAmount){
		Sales sales=new Sales();
		sales.setSalesKey(salesKey);
		sales.setTotalAmount(totalAmount);
		
		return salesRepository.save(sales);
		
	}

}
