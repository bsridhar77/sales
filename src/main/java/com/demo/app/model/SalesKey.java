package com.demo.app.model;

import java.io.Serializable;
import java.util.Date;

import com.demo.app.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SalesKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
}
