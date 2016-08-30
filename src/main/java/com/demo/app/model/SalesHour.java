package com.demo.app.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.app.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document
public class SalesHour {

@Id
String id;
@JsonSerialize(using = CustomDateSerializer.class)
Date timestamp;
String hostName;

public Date getTimestamp() {
	return timestamp;
}

public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
}

public String getHostName() {
	return hostName;
}

public void setHostName(String hostName) {
	this.hostName = hostName;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

List<SalesData> salesData;


String totalAmount;


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
