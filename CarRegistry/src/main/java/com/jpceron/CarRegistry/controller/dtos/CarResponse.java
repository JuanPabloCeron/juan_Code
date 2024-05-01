package com.jpceron.CarRegistry.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private int id;

    private BrandResponse brand;

    private String model;

    private int millage;

    private double price;

    private int year;

    private String description;

    private String colour;

    private String fueltype;

    private int numdoors;

}
