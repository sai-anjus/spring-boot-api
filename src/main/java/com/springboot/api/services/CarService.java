package com.springboot.api.services;

import java.util.List;

import com.springboot.api.entities.Car;

public interface CarService {

	List<Car> getCarsList() throws Exception;

	void save(Car car) throws Exception;
	
	Car getCarById(Long id) throws Exception;

}
