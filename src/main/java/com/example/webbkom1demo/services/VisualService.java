package com.example.webbkom1demo.services;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.webbkom1demo.URLS.Urls;
import com.example.webbkom1demo.model.DataSource;
import com.example.webbkom1demo.model.ForeCast;
import com.example.webbkom1demo.repositories.ForecastRepository;
import com.example.webbkom1demo.services.visual.vdata.Day;
import com.example.webbkom1demo.services.visual.vdata.Hour;
import com.example.webbkom1demo.services.visual.vdata.VRoot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class VisualService {
	
	private static VRoot vroot;
	
	private final ForecastRepository forecastRepository;
	
	
	public VisualService(ForecastRepository forecastRepository) {
		this.forecastRepository = forecastRepository;
	}
	
	private static List<ForeCast> forecast = new ArrayList<>();
	
	
	
	// **ENTITY DONE TABLE SET IN MYSQL**
	//Go thru the data classes and see if u have to arrange the orders, mainly understand the order
	public void fetchVisualAndSaveToDB() throws IOException {
		var objectMapper = new ObjectMapper();
		
		VRoot vroot = objectMapper.readValue(new URL(Urls.visualAPI()),
				VRoot.class);
		
		for(Day days : vroot.getDays()) {
			
			List<Hour> hours = days.getHours();
			int issnowing = days.getSnow();
			
			
			
		if(issnowing != 1 ) {
		for (Hour hour : hours) {
			boolean isSnowing = false;
			String datum = hour.getDatetime();
			float temp = hour.getTemp();
			List<String>rain = hour.getPreciptype();

			var forecastfromVisual = new ForeCast();
			forecastfromVisual.setId(UUID.randomUUID());
			forecastfromVisual.setRainOrSnow(isSnowing);
			forecastfromVisual.setPredictionTemperature(temp);
			forecastfromVisual.setLatitude(59.3154f);
			forecastfromVisual.setLongitude(18.0382f);
			forecastfromVisual.setPredictionDatum(null);
			forecastfromVisual.setPredictionHour(0);
			forecastfromVisual.setCreated(LocalDateTime.now());
			forecastfromVisual.setApiProvider(DataSource.Visual);
			forecastRepository.save(forecastfromVisual);
			

		}
	}
	
		}
	}
	
	private VRoot readFromFile() throws IOException{
		if(!Files.exists(Path.of("visualpredicitions.json"))) return new VRoot();
		ObjectMapper objectMapper = getObjectMapper();
		var jsonStr = Files.readString(Path.of("visualpredicitions.json"));
		return objectMapper.readValue(jsonStr, VRoot.class);
	}
	
	private void writeAllToFile(VRoot visualPredicitions) throws IOException {
		ObjectMapper objectMapper = getObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, visualPredicitions);
	}
	
	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}		
	
	public VRoot getVisualPredictions() {
		return vroot;
	}
	
	public void setVroot(VRoot vroot) throws IOException {
		VisualService.vroot = vroot;
		writeAllToFile(vroot);
	}

}
