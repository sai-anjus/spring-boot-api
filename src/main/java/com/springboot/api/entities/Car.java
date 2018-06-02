package com.springboot.api.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

	@Id
	private long id;
	private String model;
	private String make;
	private String type;
	private Integer makeYear;
	private Integer size;
	private Integer passengerCapacity;
	private String color;

}
