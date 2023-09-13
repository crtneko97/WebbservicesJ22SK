package com.example.webbkom1demo.services.visual.vdata;

public class CurrentConditions {
    private String datetime;
    private int datetimeEpoch;
    private float temp;
    private float humidity;
    private float precip;
    private float precipprob;
    private float snow;
    private Object preciptype;
    
    
    //Getters and setters IDE implemented
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getDatetimeEpoch() {
		return datetimeEpoch;
	}
	public void setDatetimeEpoch(int datetimeEpoch) {
		this.datetimeEpoch = datetimeEpoch;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
	public float getPrecip() {
		return precip;
	}
	public void setPrecip(float precip) {
		this.precip = precip;
	}
	public float getPrecipprob() {
		return precipprob;
	}
	public void setPrecipprob(float precipprob) {
		this.precipprob = precipprob;
	}
	public float getSnow() {
		return snow;
	}
	public void setSnow(float snow) {
		this.snow = snow;
	}
	public Object getPreciptype() {
		return preciptype;
	}
	public void setPreciptype(Object preciptype) {
		this.preciptype = preciptype;
	}
    
}
