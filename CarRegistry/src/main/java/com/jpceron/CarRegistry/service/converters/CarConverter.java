package com.jpceron.CarRegistry.service.converters;

import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.CarEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarConverter {

    private BrandConverter brandConverter =  new BrandConverter();

    public Car toCar (CarEntity carEntity){

        Car car = new Car();
        car.setId(carEntity.getId());
        car.setBrand(brandConverter.toBrand(carEntity.getBrand()));
        car.setModel(carEntity.getModel());
        car.setYear(carEntity.getYear());
        car.setColour(carEntity.getColour());
        car.setMillage(carEntity.getMillage());
        car.setDescription(carEntity.getDescription());

        return car;
    }

    public CarEntity carToEntity (Car car){

        CarEntity carEntity = new CarEntity();
        carEntity.setId(car.getId());
        carEntity.setBrand(brandConverter.toEntity(car.getBrand()));
        carEntity.setModel(car.getModel());
        carEntity.setYear(car.getYear());
        carEntity.setColour(car.getColour());
        carEntity.setMillage(car.getMillage());
        carEntity.setDescription(car.getDescription());

        return carEntity;
    }




}
