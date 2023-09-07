package com.example.webbkom1demo.services.smhi.data;

import java.util.List;
public class Geometry {
    private String type;
    private List<List<Float>> coordinates;
    
    	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<List<Float>> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<List<Float>> coordinates) {
		this.coordinates = coordinates;
	}    
    
}