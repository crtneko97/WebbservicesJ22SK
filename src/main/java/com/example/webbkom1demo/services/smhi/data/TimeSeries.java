package com.example.webbkom1demo.services.smhi.data;

import java.security.Policy.Parameters;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeSeries {
    private Date validTime;
    private List<Parameter> parameters;
    private Geometry geometry;
    
    public Date getValidTime() {
        return validTime;
    }
    public List<Parameter> getParameters() {
        return parameters;
    }
    public Geometry getGeometry() {
        return geometry;
    }
}
