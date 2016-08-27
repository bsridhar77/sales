package com.demo.app.model;

import java.util.Map;

public class CustomData {

	String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	Map<String,String> mydata;

	public Map<String, String> getMydata() {
		return mydata;
	}

	public void setMydata(Map<String, String> mydata) {
		this.mydata = mydata;
	}
}
