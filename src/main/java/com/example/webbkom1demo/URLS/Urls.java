package com.example.webbkom1demo.URLS;

public class Urls {

	
	public static String smhiAPI() {
		return "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";
	}
	public static String visualAPI() {
		return "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Liljeholmen/next24hours?unitGroup=metric&elements=datetime%2Clatitude%2Clongitude%2Ctemp%2Cpreciptype%2Csnow&include=fcst%2Chours&key=KP5XP6BDLLTJVEQVR7Z8HL4WR&contentType=json";
	}
}
