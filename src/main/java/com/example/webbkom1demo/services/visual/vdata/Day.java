package com.example.webbkom1demo.services.visual.vdata;

import java.time.LocalDate;
import java.util.List;

public class Day {
	private String datetime;
    private float temp;
    private List<String> preciptype;
    private int snow;
    private List<Hour> hours;
    
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public List<String> getPreciptype() {
		return preciptype;
	}
	public void setPreciptype(List<String> preciptype) {
		this.preciptype = preciptype;
	}
	public int getSnow() {
		return snow;
	}
	public void setSnow(int snow) {
		this.snow = snow;
	}
	public List<Hour> getHours() {
		return hours;
	}
	public void setHours(List<Hour> hours) {
		this.hours = hours;
	}
    
    
}
