package com.example.webbkom1demo.services.visual.vdata;

import java.util.ArrayList;
import java.util.List;

public class VRoot {
	private int queryCost;
    private float latitude;
    private float longitude;
    private String resolvedAddress;
    private String address;
    private String timezone;
    private int tzoffset;
    private List<Day> days;
    
	public int getQueryCost() {
		return queryCost;
	}
	public void setQueryCost(int queryCost) {
		this.queryCost = queryCost;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getResolvedAddress() {
		return resolvedAddress;
	}
	public void setResolvedAddress(String resolvedAddress) {
		this.resolvedAddress = resolvedAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public int getTzoffset() {
		return tzoffset;
	}
	public void setTzoffset(int tzoffset) {
		this.tzoffset = tzoffset;
	}
	public List<Day> getDays() {
		return days;
	}
	public void setDays(List<Day> days) {
		this.days = days;
	}
    
    
    
}
