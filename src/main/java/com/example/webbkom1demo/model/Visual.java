package com.example.webbkom1demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Lägg till entity senare
public class Visual {
		
		@Id
    	@GeneratedValue(strategy = GenerationType.UUID)
	    private UUID id;
	    private LocalDateTime created;
	    private LocalDateTime updated;
	    private String datetime;
	    private int datetimeEpoch;
	    private float temp;
	    private float humidity;
	    private float precip;
	    private float precipprob;
	    private float snow;
	    private String preciptype;
	    
	    public Visual() {}
	    
	    public Visual(UUID id) {
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

		public String getPreciptype() {
			return preciptype;
		}

		public void setPreciptype(String preciptype) {
			this.preciptype = preciptype;
		}
	    
}
