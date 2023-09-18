package com.example.webbkom1demo.services.visual.vdata;

import java.time.LocalDate;
import java.util.List;

public class Hour {
	//Ändra om till date
	   	private String datetime;
	    private float temp;
	    private int snow;
	    private List<String> preciptype;
	    
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
		public int getSnow() {
			return snow;
		}
		public void setSnow(int snow) {
			this.snow = snow;
		}
		public List<String> getPreciptype() {
			return preciptype;
		}
		public void setPreciptype(List<String> preciptype) {
			this.preciptype = preciptype;
		}
	    
	    
}
