//package com.example.webbkom1demo.controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.webbkom1demo.services.smhi.SmhiService;
//import com.example.webbkom1demo.services.smhi.data.Root;
//
//@RestController
//public class SmhiController {
//
//	
//	@Autowired
//	private SmhiService smhiService;
//	
//	@GetMapping("/api/smhi/predictions")			
//	public ResponseEntity<Root> getAll(){
//		return new ResponseEntity<Root>(smhiService.getsmhiPredictions(), HttpStatus.OK);
//	}
//	
//	
//}
