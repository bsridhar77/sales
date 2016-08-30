package com.demo.app.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SalesHourRequest {
	
	@JsonFormat(pattern="dd-MM-yyyy")
	Date timestamp;
	String time;
	String fromTime;
	
	
	
	String salesId;
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	String toTime;
	
	String hour;
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
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
		return timestamp;
	}
	public void setDate(Date date) {
		this.timestamp = date;
	}
	
	@Override
	public String toString() {
		return "SalesRequest [date=" + timestamp + ", hostname=" + hostname + ", totalAmount=" + totalAmount + ", type="
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
