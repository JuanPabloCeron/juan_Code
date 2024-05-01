package com.jpceron.CarRegistry.domain;

import com.jpceron.CarRegistry.entity.BrandEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {


    private int id;

    private Brand brand;

    private String model;

    private int millage;

    private double price;

    private int year;

    private String description;

    private String colour;

    private String fueltype;

    private int numdoors;


}
