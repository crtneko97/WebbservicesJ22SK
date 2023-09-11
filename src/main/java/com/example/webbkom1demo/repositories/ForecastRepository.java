package com.example.webbkom1demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.webbkom1demo.model.ForeCast;

@Repository
public interface ForecastRepository extends CrudRepository<ForeCast, UUID>{

	
	@Override
	List<ForeCast> findAll();
	
	//List<ForeCast> findAllBy(String part);
	
	
	@Query(value = "SELECT * FROM fore_cast f1_0 WHERE DATE(f1_0.prediction_datum) = DATE(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY))", nativeQuery = true)
	List<ForeCast> findAllForecastsForTomorrow();
	
    @Query(value = "SELECT AVG(f.prediction_temperature) FROM fore_cast f WHERE DATE(f.prediction_datum) = DATE(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY))", nativeQuery = true)
    Float calculateAverageTemperatureForTomorrow();

	
	
	
}
