package com.example.webbkom1demo.services;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webbkom1demo.URLS.Urls;
import com.example.webbkom1demo.colors.Cc;
import com.example.webbkom1demo.model.DataSource;
import com.example.webbkom1demo.model.ForeCast;
import com.example.webbkom1demo.repositories.ForecastRepository;
import com.example.webbkom1demo.services.smhi.data.Geometry;
import com.example.webbkom1demo.services.smhi.data.Parameter;
import com.example.webbkom1demo.services.smhi.data.Root;
import com.example.webbkom1demo.services.smhi.data.TimeSeries;
import com.fasterxml.jackson.databind.ObjectMapper;

//Smhi service
@Service
public class ForeCastService {
	
	private final ForecastRepository forecastRepository;
	
	@Autowired
	public ForeCastService(ForecastRepository forecastRepository) {
		this.forecastRepository = forecastRepository;
	}
	
	
	private static List<ForeCast> forecasts = new ArrayList<>();
	
	//Skapa en metod för average från databasen
	
	public List<ForeCast> getAverageByHourDate(){
		List<Object[]> forecasts = forecastRepository.calculateAverageTemperatureByHourAndDate();
		List<ForeCast> forecastList = new ArrayList<>();
		
		for (Object[] forecastData : forecasts) {
		    Integer predictionHour = (Integer) forecastData[0];
		    Date predictionDate = (Date) forecastData[1];
		    Double predictionTemp = (Double) forecastData[2];

		    if (predictionDate != null && predictionTemp != 0) {
		        // Convert java.sql.Date to java.util.Date
		        java.util.Date utilDate = new java.util.Date(predictionDate.getTime());
		        
		        // Convert java.util.Date to Instant
		        Instant instant = utilDate.toInstant();
		        
		        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		        ForeCast forecast = new ForeCast();
		        forecast.setPredictionHour(predictionHour);
		        forecast.setPredictionDatum(localDate);
		        forecast.setPredictionTemperature(predictionTemp.floatValue());    
		        forecastList.add(forecast);
		    }
	 
		}
		
		return forecastList;
	}
		
	
	
	public List<ForeCast> getForecastFromDBonedayahead() {
	    List<ForeCast> forecasts = forecastRepository.findAllForecastsForTomorrow();
	    
	    for (ForeCast forecast : forecasts) {
	    	if(forecast.getPredictionTemperature() != 0) {
	    		System.out.println(Cc.YE+"Forecast ID: " + forecast.getId());
	    		System.out.println("Longitude: " + forecast.getLongitude());
	    		System.out.println("Latitude: " + forecast.getLatitude());
	    		System.out.println("Temperature: " + forecast.getPredictionTemperature());
	    		System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
	    		System.out.println("-----------"+Cc.RES);
	    	}
	    }
	    
	    return forecasts;
	}
	
	public List<ForeCast> getForecastfromDB() {
	    List<ForeCast> forecasts = forecastRepository.findAll();
	    
	    for (ForeCast forecast : forecasts) {
	    	if(forecast.getPredictionTemperature() != 0) {

	        System.out.println(Cc.C+"Forecast ID: " + forecast.getId());
	        System.out.println("Longitude: " + forecast.getLongitude());
	        System.out.println("Latitude: " + forecast.getLatitude());
	        System.out.println("Temperature: " + forecast.getPredictionTemperature());
	        System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
	        System.out.println("-----------"+Cc.RES);
	    	}
	    }
	    
	    return forecasts;
	}
	
	//Random metod jag gjorde
	public List<ForeCast> getForecastsWithCloseTemperatures(float targetTemperature, float tolerance) {
        List<ForeCast> forecasts = forecastRepository.findAll();
        List<ForeCast> closeTemperatureForecasts = new ArrayList<>();

        for (ForeCast forecast : forecasts) {
            float temperature = forecast.getPredictionTemperature();

            // Check if the temperature is within the specified tolerance range
            if (Math.abs(temperature - targetTemperature) <= tolerance) {
                closeTemperatureForecasts.add(forecast);
                
                System.out.println("Forecast ID: " + forecast.getId());
                System.out.println("Longitude: " + forecast.getLongitude());
                System.out.println("Latitude: " + forecast.getLatitude());
                System.out.println("Prediction Temperature: " + forecast.getPredictionTemperature());
                System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
                System.out.println("-----------");
            }
        }

        return closeTemperatureForecasts;
    }
	
	//Retunerar alla temps imorgon bara.
	  public Float calculateAverageTemperatureForTomorrowUsingQuery() {
	        return forecastRepository.calculateAverageTemperatureForTomorrow();
	    }
	//Retunerar all temp tillsammans inte beroende på dag
	public float calculateAverageTemperature() {
	    List<ForeCast> forecasts = forecastRepository.findAll();
	    float sum = 0.0f;
	    int count = 0;

	    for (ForeCast forecast : forecasts) {
	        float temperature = forecast.getPredictionTemperature();
	        sum += temperature;
	        count++;
	    }
	    if (count == 0) {
	        return 0.0f;
	    }

	    return sum / count;
	}
	
	
	public void fetchAndSaveToDB() throws IOException {
			var objectMapper = new ObjectMapper();
			
			Root root = objectMapper.readValue(new URL(Urls.smhiAPI()),
					Root.class);
			List<TimeSeries> timeseriesList = root.getTimeseries();
				
			Date currentTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentTime);
			calendar.add(Calendar.HOUR_OF_DAY, 25);
			Date tomorrow = calendar.getTime();
			for (TimeSeries timeSeries : timeseriesList) {
				Date validTime = timeSeries.getValidTime();
				calendar.setTime(validTime);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
				
				LocalDate validLocalDate = validTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				if(validTime.after(currentTime) && validTime.before(tomorrow) && 
						hour == currentHour) {
					for(Parameter param : timeSeries.getParameters()) {
						String paraName = param.getName();
						var forecastFromSmhi = new ForeCast();
						List<Float> values = param.getValues();
						
						Boolean rainOrSnow = false;
						float latitude = 1.0f;
						float longitude = 1.0f;
						for(Float paramValue :values) {
							if("t".equals(paraName) || "pcat".equals(paraName)) 
							{
								if(paramValue == 3.0 && paramValue == 1) {
									rainOrSnow = true;
								}
							}
							
							Geometry geometry = root.getGeometry();
					        List<List<Float>> coordinates = geometry.getCoordinates();
							for(List<Float> coordinate : coordinates) {
								latitude = coordinate.get(1);
								longitude = coordinate.get(0);
							}
							
							if ("t".equals(paraName)) {
								System.out.println("tid: " + hour);
								System.out.println("temp: " + paramValue);
								System.out.println("tid: " + validLocalDate);
								System.out.println("latitude: ");
								System.out.println("tid: " + validLocalDate);
								
								forecastFromSmhi.setId(UUID.randomUUID());
				                forecastFromSmhi.setRainOrSnow(rainOrSnow);
				                forecastFromSmhi.setPredictionTemperature(paramValue);
				                forecastFromSmhi.setLatitude(latitude);
				                forecastFromSmhi.setLongitude(longitude);
				                forecastFromSmhi.setPredictionDatum(validLocalDate);
				                forecastFromSmhi.setPredictionHour(hour);
				                forecastFromSmhi.setCreated(LocalDateTime.now());
				                forecastFromSmhi.setApiProvider(DataSource.Smhi);
				                forecastRepository.save(forecastFromSmhi);
							}
						}
					}
				}
			}
	}

	public ForeCast add(ForeCast forecast) {
		forecastRepository.save(forecast);
		return forecast;
	}
	public ForeCast getIndexBy(int i) {
		return null;
	}
	public void update(ForeCast forecastFromUser) throws IOException {
		forecastRepository.save(forecastFromUser);
	}
	public Optional<ForeCast> get(UUID id){
		return forecastRepository.findById(id);
//      return getForecasts().stream().filter(forecast -> forecast.getId().equals(id))
//      .findFirst();
	}
	
	public void getAllOnDate(LocalDate now) {
			//return forecastRepository;
		
	}

}
