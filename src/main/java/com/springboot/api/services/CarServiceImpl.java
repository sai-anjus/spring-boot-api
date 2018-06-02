package com.springboot.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.entities.Car;
import com.springboot.api.repositories.CarRepository;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	CarRepository carRepository;

	@Override
	public List<Car> getCarsList() throws Exception {
		return carRepository.findAll();
	}

	@Override
	public void save(Car car) throws Exception {
		carRepository.save(car);
	}

	@Override
	public Car getCarById(Long id) throws Exception {
		return carRepository.findById(id).orElse(null);
	}

}
