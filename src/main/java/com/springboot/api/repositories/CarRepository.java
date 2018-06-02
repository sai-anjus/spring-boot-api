package com.springboot.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.api.entities.Car;

@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

}
