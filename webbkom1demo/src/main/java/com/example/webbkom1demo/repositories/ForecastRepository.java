package com.example.webbkom1demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.webbkom1demo.model.ForeCast;

@Repository
public interface ForecastRepository extends CrudRepository<ForeCast, UUID>{

	
	@Override
	List<ForeCast> findAll();
	
	//List<ForeCast> findAllBy(String part);
	
}
