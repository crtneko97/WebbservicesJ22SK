package com.example.webbkom1demo.services.smhi.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Parameter {
    private String name;  
    private String unit;
    private String levelType;
    private int level;
    private List<Float> values;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<Float> getValues() {
		return values;
	}
	public void setValues(List<Float> values) {
		this.values = values;
	}
    
    
}
