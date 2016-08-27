package com.demo.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.demo.app.model.Sales;
import com.demo.app.model.SalesKey;


public interface SalesRepository extends MongoRepository<Sales, String> {
	@Query(value = "{ '_id' : ?0 }")
	public Sales filterBySalesKey(SalesKey salesKey);
	
	
	
}
 