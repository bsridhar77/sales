package com.demo.app.model;

import java.util.Arrays;
import java.util.Date;

import com.demo.app.util.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ResponseData {

	String[] mydata;
	 /*@JsonFormat(pattern="yyyy.MM.dd G 'at' HH:mm:ss z")*/
	@JsonSerialize(using = CustomDateSerializer.class)
	Date timestamp;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ResponseData [mydata=" + Arrays.toString(mydata) + "]";
	}

	public String[] getMydata() {
		return mydata;
	}

	public void setMydata(String[] mydata) {
		this.mydata = mydata;
	}
}
