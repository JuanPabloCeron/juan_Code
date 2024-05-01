package com.jpceron.CarRegistry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idbrand")
    private BrandEntity brand;

    private String model;


    private int millage;


    private double price;


    private int year;


    private String description;


    private String colour;


    private String fueltype;


    private int numdoors;


}
