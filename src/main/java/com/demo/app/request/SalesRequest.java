package com.demo.app.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SalesRequest {
	
	@JsonFormat(pattern="dd-MM-yyyy hh:mm")
	Date date;
	String hostname;
	String totalAmount;
	String type;
	String volume;
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "SalesRequest [date=" + date + ", hostname=" + hostname + ", totalAmount=" + totalAmount + ", type="
				+ type + ", volume=" + volume + "]";
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
