package com.example.webbkom1demo.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbkom1demo.dto.ForecastListDTO;
import com.example.webbkom1demo.dto.NewForecastDTO;
import com.example.webbkom1demo.model.DataSource;
import com.example.webbkom1demo.model.ForeCast;
import com.example.webbkom1demo.services.ForeCastService;

@RestController
public class ForeCastController {

	@Autowired
	private ForeCastService forecastService;
	
	@GetMapping("/api/forecasts/")			
	public ResponseEntity<List<ForecastListDTO>> getAll(){		
		return new ResponseEntity<List<ForecastListDTO>>(forecastService.getForecast().stream().map(forecast->{
			var forecastListDTO = new ForecastListDTO();
			forecastListDTO.Id = forecast.getId();
			forecastListDTO.Date = forecast.getPredictionDatum();
			forecastListDTO.Temperature = forecast.getPredictionTemperature();
			forecastListDTO.Hour = forecast.getPredictionHour();
			return forecastListDTO;
		}).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping("/api/forecasts/{id}")
	public ResponseEntity<ForeCast> get(@PathVariable UUID id){
		Optional<ForeCast> forecast = forecastService.get(id);
		if(forecast.isPresent()) return ResponseEntity.ok(forecast.get());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/api/forecasts")
	public ResponseEntity newForecast (@RequestBody NewForecastDTO forecastDTO) throws IOException {
	var forecast = new ForeCast();
	forecast.setId(UUID.randomUUID());
	forecast.setPredictionDatum(forecastDTO.getDate());
	forecast.setPredictionTemperature(forecastDTO.getTemperature());
	forecast.setPredictionHour(forecastDTO.getHour());
	var newCreated = forecastService.add(forecast);
	return ResponseEntity.ok(newCreated);
	}
	
	
	@PutMapping("/api/forecasts/{id}")
	public ResponseEntity Update(@PathVariable UUID id, @RequestBody NewForecastDTO newforecastdto) throws IOException {
	var forecast = forecastService.get(id).get();

	forecast.setId(id);
	forecast.setPredictionDatum(newforecastdto.getDate());
	forecast.setPredictionTemperature(newforecastdto.getTemperature());
	forecast.setPredictionHour(newforecastdto.getHour());

	forecastService.update(forecast);
	return ResponseEntity.ok(forecast);
	}
    
}
