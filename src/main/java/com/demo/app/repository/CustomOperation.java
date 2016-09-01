package com.demo.app.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.DBObject;

public class CustomOperation implements AggregationOperation {
    private DBObject operation;

    public CustomOperation (DBObject operation) {
        this.operation = operation;
    }

    public CustomOperation() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public DBObject toDBObject(AggregationOperationContext context) {
        return context.getMappedObject(operation);
    }
}