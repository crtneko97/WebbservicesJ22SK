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


/*
 * Man skapar ett nytt DTO baserat på vem som använder tjänsten.
 * 
 * Kan man inte via repositoryt begränsa vad som ska visas och använda det i controllern?
 */

	// 1, Clienbt anropar /api/forecasts GET
	// 2, spring kolalr vilken funktion hanterar denna /api/forecasts
	// 3, spring anropar den funktionen
	// 3.5 Koden körs
	// 4. Spring tar det som funktionen returnar och gör till JSON
	// 5. Spring skickar tillbaka JSON till client


	// varför gör man det som en stream och inte bara en for loop


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
	
	/* Den äldre get metoden
	@GetMapping("/api/forecasts/{id}")
	  public ResponseEntity<ForeCast> Get(@PathVariable UUID id){
        Optional<ForeCast> forecast = forecastService.get(id);
        if(forecast.isPresent()) return ResponseEntity.ok(forecast.get());
        return  ResponseEntity.notFound().build();
    }
    */
	
//	@GetMapping("/api/forecasts/{id}")
//	public ResponseEntity<ForeCast> get(@PathVariable UUID id){
//		Optional<ForeCast> forecast = forecastService.get(id);
//		if(forecast.isPresent()) return ResponseEntity.ok(forecast.get());
//		return ResponseEntity.notFound().build();
//	}
	
//	@PutMapping("/api/forecasts/{id}")
//	public ResponseEntity<ForeCast> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO) throws IOException{
//		var forecast = new ForeCast();
//		forecast.setId(id);
//		forecast.setDate(newForecastDTO.getDate());
//		forecast.setHour(newForecastDTO.getHour());
//		forecast.setTemperature(newForecastDTO.getTemperature());
//		forecast.setLastModifiedBy("simp K");
//		forecastService.update(forecast);
//		return ResponseEntity.ok(forecast);
//	}
	@PostMapping("/api/forecasts")
	public ResponseEntity<ForeCast> newForecast (@RequestBody ForeCast forecast) throws IOException {
		var newCreated = forecastService.add(forecast);
		return ResponseEntity.ok(newCreated);
	}
	
	
	@PutMapping("/api/forecasts/{id}")
    public ResponseEntity<ForeCast> Update(@PathVariable UUID id, @RequestBody NewForecastDTO newforecastdto) throws IOException {
        var forecast = new ForeCast();
        
        forecast.setId(id);
        forecast.setPredictionDatum(newforecastdto.getDate());
        forecast.setPredictionHour(newforecastdto.getHour());
        forecast.setPredictionTemperature(newforecastdto.getTemperature());
        //lägg till allt annat sen när du pallar :) :P 
//        
//        forecastFromSmhi.setRainOrSnow(rainOrSnow);
//        forecastFromSmhi.setPredictionTemperature(paramValue);
//        forecastFromSmhi.setLatitude(latitude);
//        forecastFromSmhi.setLongitude(longitude);
//        forecastFromSmhi.setCreated(LocalDateTime.now());
//        forecastFromSmhi.setApiProvider(DataSource.Smhi);
        
        
		forecastService.update(forecast);
        return ResponseEntity.ok(forecast);
    }
    
    
}
