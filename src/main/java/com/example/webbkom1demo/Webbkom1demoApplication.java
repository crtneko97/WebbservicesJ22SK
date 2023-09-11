package com.example.webbkom1demo;

import com.example.webbkom1demo.URLS.Urls;
import com.example.webbkom1demo.colors.Cc;
import com.example.webbkom1demo.menu.Menu;
import com.example.webbkom1demo.model.DataSource;
import com.example.webbkom1demo.model.ForeCast;
import com.example.webbkom1demo.repositories.ForecastRepository;
import com.example.webbkom1demo.services.ForeCastService;
import com.example.webbkom1demo.services.smhi.SmhiService;
import com.example.webbkom1demo.services.smhi.data.Geometry;
import com.example.webbkom1demo.services.smhi.data.Parameter;
import com.example.webbkom1demo.services.smhi.data.Root;
import com.example.webbkom1demo.services.smhi.data.TimeSeries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.time.LocalDateTime;

@SpringBootApplication
public class Webbkom1demoApplication implements CommandLineRunner{

@Autowired
private ForeCastService forecastService;


@Autowired 
private SmhiService smhiService;
		
	
	public static void main(String[] args) {
		SpringApplication.run(Webbkom1demoApplication.class, args);
	}

		boolean isRunning = true;

	@Override
	public void run(String... args) throws Exception {
		var objectMapper = new ObjectMapper();
		
		Root root = objectMapper.readValue(new URL(Urls.smhiAPI()), 
				Root.class);
		
		var scan = new Scanner(System.in);
		
		while(true){
			System.out.println(Menu.currentD());
			System.out.println(Menu.menu());
			int sel = scan.nextInt();
			if(sel == 1){forecastService.fetchAndSaveToDB();}
			else if(sel == 2){forecastService.getForecastfromDB();}
			else if(sel == 3) {listSMHI();}
			else if(sel == 4) {listSMHIoneDayAhead();}
			else if(sel == 5) {addPrediction(scan);}
			else if(sel == 8) {forecastService.getForecastFromDBonedayahead();}
			else if(sel == 9) {listAverageTempOneDayAhead();}
			else if(sel == 10) {System.out.println(Menu.urlsWeb());}
			else if(sel == 100){break;}
		}
	}
	
	private void listAverageTempOneDayAhead() throws IOException{
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tmrw = now.plusDays(1);
		String formatedDate = tmrw.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		
		System.out.println("\n\nDate: " + formatedDate
				+ "\nAverage temp: " + forecastService.calculateAverageTemperature());
		
	}


	private void listSMHIoneDayAhead() throws IOException{
		var objectMapper = new ObjectMapper();
		
		Root root = objectMapper.readValue(new URL(Urls.smhiAPI()), 
				Root.class);
		
		System.out.println("Approved time: " + root.getApprovedTime()
						+ "Reference time: " + root.getReferenceTime()
						+ "Location: " + root.getGeometry());
		
		long currentTime = System.currentTimeMillis();
		long timeCalc = 24 * 60 * 60 * 1000;
		
		for(TimeSeries timeseries : root.getTimeseries()) {
			long forecastTime = timeseries.getValidTime().getTime();			
			if(forecastTime > currentTime && forecastTime <= currentTime + timeCalc) {
				System.out.println("Validtime: " + timeseries.getValidTime()+"\n");
			
			
			for(Parameter parameter : timeseries.getParameters()) {
				String paraName = parameter.getName();
				if (paraName.equals("t") || paraName.equals("pcat")) {
	        	    System.out.printf("Parameter Name: %s%s%s%n", Cc.GR, parameter.getName(), Cc.C);
	        	    System.out.printf("Parameter Unit: %s%s%s%n", Cc.GR, parameter.getUnit(), Cc.C);
	        	    System.out.printf("Parameter level: %s%s%s%n", Cc.GR, parameter.getLevel(), Cc.C);
	        	    System.out.printf("Parameter values: %s%s%s%n", Cc.GR, parameter.getValues(), Cc.C);
	        	    System.out.println("-----------------------");		
	        	}
				
			}
			
		}
			if(forecastTime > currentTime + timeCalc) {
				break;
				}
		}
	}
	
	
	private void addPrediction(Scanner scan) throws IOException {

		System.out.println("*** CREATE PREDICTION ***");
		System.out.println("Enter longitude: ");
		float longitude = scan.nextFloat();
		System.out.println("Enter latitude:");
		float latitude = scan.nextFloat();
		System.out.print("Enter prediction date:");
		int pdate=  scan.nextInt() ;
		System.out.print("Enter predicition hour:");
		int phour=  scan.nextInt();
		System.out.println("Enter prediction temperature:");
		int ptemp = scan.nextInt();
		System.out.println("Rain or snowing? 1=snow / 2=rain");
		boolean rainorsnow;
		if(scan.nextInt() == 1) {
			rainorsnow = true;
		}else {
			rainorsnow = false;
		}
		var forecast = new ForeCast();
		forecast.setId(UUID.randomUUID());
		forecast.setCreated(LocalDateTime.now());
		forecast.setLongitude(longitude);
		forecast.setLatitude(latitude);
		forecast.setPredictionHour(phour);
		forecast.setPredictionTemperature(ptemp);
		forecast.setRainOrSnow(rainorsnow);
		forecastService.add(forecast);
	}
	
	private void listSMHI() {
		var objectMapper = new ObjectMapper();
		try {
			Root root = objectMapper.readValue(new URL(Urls.smhiAPI()), 
					Root.class);
			
			for (TimeSeries timeSeries : root.getTimeseries()) {
			    System.out.printf("Timestamp: %s%s%s%n", Cc.GR, timeSeries.getValidTime(), Cc.C);

			    List<Parameter> parameters = timeSeries.getParameters();
			    if (parameters != null) {
			        for (Parameter parameter : parameters) {
			        	String paraName = parameter.getName();
			        	if (paraName.equals("t") || paraName.equals("pcat")) {
			        	    System.out.printf("Parameter Name: %s%s%s%n", Cc.GR, parameter.getName(), Cc.C);
			        	    System.out.printf("Parameter Unit: %s%s%s%n", Cc.GR, parameter.getUnit(), Cc.C);
			        	    System.out.printf("Parameter level: %s%s%s%n", Cc.GR, parameter.getLevel(), Cc.C);
			        	    System.out.printf("Parameter values: %s%s%s%n", Cc.GR, parameter.getValues(), Cc.C);
			        	    System.out.println("-----------------------");		
			        	}
			          
			        }
			    } else {System.out.println("No parameters available.");}
			    Geometry geometry = root.getGeometry();
			    if (geometry != null) {
			        System.out.printf("Geometry Type: %s%n",geometry.getType());
			        List<List<Float>> coordinates = geometry.getCoordinates();
			        System.out.println("Coordinates:");
			        for (List<Float> coordinate : coordinates) {
			            System.out.printf("  Latitude: %f%n", coordinate.get(1));
			            System.out.printf("  Longitude: %f%n", coordinate.get(0));
			        }
			        System.out.println("-----------------------");
			    } else {System.out.println("No geometry information available.");}
			    
			    System.out.println(Cc.RES);
			    System.out.println("-----------------------");
			    
			    System.out.printf("geometry-size: %s%nparam-size: %s%n",geometry.getCoordinates().size(), parameters.size());;
			    smhiService.setRoot(root);
			    
			}
		}catch(IOException e) {e.printStackTrace();}
	}
	
}	

