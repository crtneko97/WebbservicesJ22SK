package com.example.webbkom1demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ForeCast {
	
	@Id
	@GeneratedValue(strategy= GenerationType.UUID)
	private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private float longitude;
    private float latitude;
    private LocalDate predictionDatum; //20230616
    private int predictionHour; //8
    private float predictionTemperature;
    private boolean rainOrSnow;
    private DataSource dataSource;
   public ForeCast() {}
  
    public ForeCast(UUID id) {
    	this.id = id;
    	this.created = LocalDateTime.now();
    }
    
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getUpdated() {
		return updated;
	}
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public LocalDate getPredictionDatum() {
		return predictionDatum;
	}
	public void setPredictionDatum(LocalDate validLocalDate) {
		this.predictionDatum = validLocalDate;
	}
	public int getPredictionHour() {
		return predictionHour;
	}
	public void setPredictionHour(int predictionHour) {
		this.predictionHour = predictionHour;
	}
	public float getPredictionTemperature() {
		return predictionTemperature;
	}
	public void setPredictionTemperature(float predictionTemperature) {
		this.predictionTemperature = predictionTemperature;
	}
	public boolean isRainOrSnow() {
		return rainOrSnow;
	}
	public void setRainOrSnow(boolean rainOrSnow) {
		this.rainOrSnow = rainOrSnow;
	}
	public DataSource getApiProvider() {
		return dataSource;
	}
	public void setApiProvider(DataSource apiProvider) {
		this.dataSource = apiProvider;
	}
			

}
