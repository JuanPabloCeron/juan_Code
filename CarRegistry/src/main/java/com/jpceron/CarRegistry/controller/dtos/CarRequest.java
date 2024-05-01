package com.jpceron.CarRegistry.controller.dtos;

import com.jpceron.CarRegistry.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private int id;

    private BrandRequest brandRequest;

    private String model;

    private int millage;

    private double price;

    private int year;

    private String description;

    private String colour;

    private String fueltype;

    private int numdoors;

}
