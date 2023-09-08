package com.example.webbkom1demo.services;

import java.io.IOException;
import java.net.URL;
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

@Service
public class ForeCastService {
	
	private final ForecastRepository forecastRepository;
	@Autowired
	public ForeCastService(ForecastRepository forecastRepository) {
		this.forecastRepository = forecastRepository;
	}
	
	
	private static List<ForeCast> forecasts = new ArrayList<>();
	
	
//	public List<ForeCast> getForecastFromDBonedayahead() {
//	    List<ForeCast> forecasts = forecastRepository.findAlloneDayahead();
//	    
//	    for (ForeCast forecast : forecasts) {
//	        System.out.println("Forecast ID: " + forecast.getId());
//	        System.out.println("Longitude: " + forecast.getLongitude());
//	        System.out.println("Latitude: " + forecast.getLatitude());
//	        System.out.println("Temperature: " + forecast.getPredictionTemperature());
//	        System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
//	        System.out.println("-----------");
//	    }
//	    
//	    return forecasts;
//	}
	public List<ForeCast> getForecastFromDBonedayahead() {
	    List<ForeCast> forecasts = forecastRepository.findAllForecastsForTomorrow();
	    
	    for (ForeCast forecast : forecasts) {
	        System.out.println("Forecast ID: " + forecast.getId());
	        System.out.println("Longitude: " + forecast.getLongitude());
	        System.out.println("Latitude: " + forecast.getLatitude());
	        System.out.println("Temperature: " + forecast.getPredictionTemperature());
	        System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
	        System.out.println("-----------");
	    }
	    
	    return forecasts;
	}
	
	public List<ForeCast> getForecastfromDB() {
	    List<ForeCast> forecasts = forecastRepository.findAll();
	    
	    for (ForeCast forecast : forecasts) {
	        System.out.println("Forecast ID: " + forecast.getId());
	        System.out.println("Longitude: " + forecast.getLongitude());
	        System.out.println("Latitude: " + forecast.getLatitude());
	        System.out.println("Temperature: " + forecast.getPredictionTemperature());
	        System.out.println("Prediction Datum: " + forecast.getPredictionDatum());
	        System.out.println("-----------");
	    }
	    
	    return forecasts;
	}
	public List<ForeCast> getForecastsWithCloseTemperatures(float targetTemperature, float tolerance) {
        List<ForeCast> forecasts = forecastRepository.findAll();
        List<ForeCast> closeTemperatureForecasts = new ArrayList<>();

        for (ForeCast forecast : forecasts) {
            float temperature = forecast.getPredictionTemperature();

            // Check if the temperature is within the specified tolerance range
            if (Math.abs(temperature - targetTemperature) <= tolerance) {
                closeTemperatureForecasts.add(forecast);
                
                // Print the forecast details to the console
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
	
	public float calculateAverageTemperature() {
	    List<ForeCast> forecasts = forecastRepository.findAll();

	    float sum = 0.0f;
	    int count = 0;

	    for (ForeCast forecast : forecasts) {
	        // Assuming 'getPredictionTemperature()' returns the temperature value as float
	        float temperature = forecast.getPredictionTemperature();
	        sum += temperature;
	        count++;
	    }

	    if (count == 0) {
	        // To avoid division by zero
	        return 0.0f;
	    }

	    // Calculate the average temperature
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
	
	
  
    //Gammal kod    
//    private List<ForeCast> readFromFile() throws IOException {
//        if(!Files.exists(Path.of("predictions.json"))) return new ArrayList<ForeCast>();
//        ObjectMapper objectMapper = getObjectMapper();
//        var jsonStr = Files.readString(Path.of("predictions.json"));
//        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, ForeCast[].class ) ));
//    }
//
//
//    private void writeAllToFile(List<ForeCast> weatherPredictions) throws IOException {
//        ObjectMapper objectMapper = getObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//
//        StringWriter stringWriter = new StringWriter();
//        objectMapper.writeValue(stringWriter, weatherPredictions);
//
//        Files.writeString(Path.of("predictions.json"), stringWriter.toString());
//    }
//
//
//    private static ObjectMapper getObjectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        //mapper.registerModule(new JavaTimeModule());
//        return mapper;
//    }
//
//    public List<ForeCast> getForecasts(){
//        return forecasts;
//    }
//    
//    public ForeCast add(ForeCast forecast) throws IOException {
//    	forecast.setId(UUID.randomUUID());
//    	forecasts.add(forecast);
//    	writeAllToFile(forecasts);
//    	return forecast;
//    }
//    /*
//    public void add(ForeCast forecast) throws IOException {
//        forecasts.add(forecast);
//        writeAllToFile(forecasts);
//    }
//    */
//
//    public ForeCast getByIndex(int i) {
//        return forecasts.get(i);
//    }
//
//    public void update(ForeCast forecast) throws IOException {
//        writeAllToFile(forecasts);
//    }
//	
//    public void update2(ForeCast forecastFromUser) throws IOException {
//        
//        var foreCastInList = get(forecastFromUser.getId()).get();
//        foreCastInList.setTemperature(forecastFromUser.getTemperature());
//        foreCastInList.setDate(forecastFromUser.getDate());
//        foreCastInList.setHour(forecastFromUser.getHour());
//        writeAllToFile(forecasts);
//    }   
//    
//	public Optional<ForeCast> get(UUID id){
//		return getForecasts().stream().filter(forecast -> forecast.getId().equals(id))
//                .findFirst();
//	}
}
