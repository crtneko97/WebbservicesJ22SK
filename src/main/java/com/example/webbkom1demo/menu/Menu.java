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
				+ "1: fetch weatherprediction api and save it to MYSQL database\n"
				+ "2: list weather prediction from MYSQL database\n"
				+ "3: list weather predicition directly from SMHI api\n"
				+ "4: list weather prediction one day ahead from SMHI api\n"
				+ "5: add your own preidiction to the api/database by input\n"
				+ "8: list weather prediction one day ahead from database\n"
				+ "9: list the average predicted temperature for tomorrow\n"
				+ "10: list the following getmappings\n"
				+"99: List VISUAL API(WORKING)**STILL IN PROGRESS**\n"
				+ "100: break the loop";
	}
	
	public static String urlsWeb() {
		return "- /api/forecasts/ \n"
				+ "- /average-temperature-tomorrow \n"
				+ "- /api/forecasts/onedayahead \n"
				+ "- /api/forecasts/{id} \n";
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