package com.demo.app.model;

import java.util.Arrays;

public class SalesData {

	String type;
	String[] volume;
	@Override
	public String toString() {
		return "SalesData [type=" + type + ", volume=" + Arrays.toString(volume) + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getVolume() {
		return volume;
	}
	public void setVolume(String[] volume) {
		this.volume = volume;
	}
	
}
