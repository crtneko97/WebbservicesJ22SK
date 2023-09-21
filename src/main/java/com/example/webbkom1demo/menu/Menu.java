package com.example.webbkom1demo.menu;

import java.util.Date;

import com.example.webbkom1demo.colors.Cc;

public class Menu {
		
	
	
	public static String currentD() {
		Date currentDate = new Date();
		return  Cc.YE+"\n\n\n"+currentDate+"\n"+Cc.RES;
	}
	
	public static String menu() {
		return  "\n***School project***\nCourse: Webservices in java\nTeacher: Stefan HolmBerg"
				+"\nSchool: Stockholms Teknsika Institut\nStudent: Simon Kern \nGithub: https://github.com/crtneko97"
				+"\n***Commands***\n"
				+Cc.GR+ "1: fetch weatherprediction api and save it to MYSQL database\n"+Cc.RES
				+"2: list weather prediction from MYSQL database\n"
				+ Cc.YE +"3: list weather predicition directly from SMHI api\n"+Cc.RES
				+ Cc.YE +"4: list weather prediction one day ahead from SMHI api\n"+Cc.RES
				+ "5: add your own preidiction to the api/database by input\n"
				+ "6: List average temp for all days that's been saved in the database.\n"
				+ "8: list weather prediction one day ahead from database\n"
				+ "9: list the average predicted temperature for tomorrow\n"
				+ "10: list the following getmappings\n"
				+Cc.GR+ "98: Fetch visualforecast to database **WORKING**\n"+Cc.RES
				+Cc.YE+"99: List VISUAL API(WORKING)**WORKING**\n"+Cc.RES
				+ "100: break the loop";
	}
	
	public static String urlsWeb() {
		return "- /api/forecasts/ \n"
				+ "- /average-temperature-tomorrow \n"
				+ "- /api/forecasts/onedayahead \n"
				+ "- /api/forecasts/{id} \n"
				+ "- /api/forecasts/average-hour-date \n";
	}
}
/*
var forecast = new ForeCast();
forecast.setId(UUID.randomUUID());
forecast.setTemperature(12f);
forecast.setDate(20230101);
forecast.setHour(12);
String json = objectMapper.writeValueAsString(forecast);
System.out.println(json);
Lägg till en forecast till fil **TEMPORÄR**		
var userReg = new UserReg();
userReg.setFirstName("simon");
userReg.setLastName("kern");
userReg.setCountry("Sweden");
userReg.setCity("Stockholm");
String json2 = objectMapper.writeValueAsString(userReg);
System.out.println(json2);
ForeCast forecast2 = objectMapper.readValue(json,ForeCast.class);
*/