package com.example.webbkom1demo.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbkom1demo.URLS.Urls;
import com.example.webbkom1demo.model.Visual;
import com.example.webbkom1demo.repositories.ForecastRepository;
import com.example.webbkom1demo.services.visual.vdata.VRoot;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VisualService {
	
	private final ForecastRepository forecastRepository;
	
	public VisualService(ForecastRepository forecastRepository) {
		this.forecastRepository = forecastRepository;
	}
	
	private static List<Visual> visual = new ArrayList<>();
	
	//Remeber to make entity for Visual in model package later.
	public void fetchVisualAndSaveToDB() throws IOException {
		var objectMapper = new ObjectMapper();
		
		VRoot vroot = objectMapper.readValue(new URL(Urls.visualAPI()),
				VRoot.class);
		
	}

}
