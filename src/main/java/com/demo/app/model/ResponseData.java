package com.demo.app.model;

import java.util.Arrays;

public class ResponseData {

	String[] mydata;

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
