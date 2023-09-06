package com.example.webbkom1demo.menu;

import java.util.Date;

import com.example.webbkom1demo.colors.Cc;

public class Menu {
		
	
	
	public static String currentD() {
		Date currentDate = new Date();
		return  Cc.YE+"\n\n\n"+currentDate+"\n"+Cc.RES;
	}
	
	public static String menu() {
		return  
				"1. Add prediction to database\t"
				+"2. Select from database\n"
				+"3. List SMHI Api\t"
				+"4. List SMHI one day ahead."
				+ "\n\n100. Exit";
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