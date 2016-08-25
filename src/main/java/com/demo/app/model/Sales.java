package com.demo.app.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sales {

@Id
SalesKey salesKey;

List<SalesData> salesData;

String totalAmount;

public SalesKey getSalesKey() {
	return salesKey;
}

public void setSalesKey(SalesKey salesKey) {
	this.salesKey = salesKey;
}

public List<SalesData> getSalesData() {
	return salesData;
}

public void setSalesData(List<SalesData> salesData) {
	this.salesData = salesData;
}

public String getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}
}
