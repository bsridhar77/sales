package com.demo.app.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesHour;


public interface SalesHourRepository extends MongoRepository<SalesHour, String> {
	@Query(value = "{ 'timestamp' : ?0 , 'hostName' : ?1 }")
	public Sales findBySalesKey(Date timestamp,String hostname);
	
	
	
}
 