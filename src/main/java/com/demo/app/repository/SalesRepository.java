package com.demo.app.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.demo.app.model.Sales;


public interface SalesRepository extends MongoRepository<Sales, String> {
	@Query(value = "{ 'timestamp' : ?0 , 'hostNme' : ?1 }")
	public Sales findBySalesKey(Date timestamp,String hostname);
	
	
	
}
 