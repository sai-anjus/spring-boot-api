package com.springboot.api.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.entities.Car;
import com.springboot.api.services.CarService;

@RestController
@RequestMapping("/api")
public class CarRestController {

	@Autowired
	CarService carService;

	@RequestMapping(path = "/getCars", method = RequestMethod.GET)
	public List<Car> getCarList() throws Exception {
		try {
			return carService.getCarsList();
		} catch (Exception ex) {
			throw ex;
		}
	}

	@RequestMapping(path = "/car", method = RequestMethod.GET)
	public Car getCarById(@RequestParam("carId") Long id) throws Exception {

		return carService.getCarById(id);

	}

	@RequestMapping(path = "/car", method = RequestMethod.POST)
	public void addUpdateCar(@ModelAttribute("carObj") Car car) throws Exception {

		carService.save(car);

	}

}
