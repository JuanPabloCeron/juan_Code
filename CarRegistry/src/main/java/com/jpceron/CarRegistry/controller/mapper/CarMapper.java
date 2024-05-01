package com.jpceron.CarRegistry.controller.mapper;
import com.jpceron.CarRegistry.controller.dtos.CarRequest;
import com.jpceron.CarRegistry.controller.dtos.CarResponse;
import com.jpceron.CarRegistry.domain.Car;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarMapper {

    private BrandMapper brandMapper=new BrandMapper();

    public CarResponse toCarResponse (Car carEntity){

        CarResponse car = new CarResponse();

        car.setId(carEntity.getId());
        car.setBrand(brandMapper.toResponse(carEntity.getBrand()));
        car.setModel(carEntity.getModel());
        car.setYear(carEntity.getYear());
        car.setColour(carEntity.getColour());
        car.setMillage(carEntity.getMillage());
        car.setDescription(carEntity.getDescription());
        car.setFueltype(carEntity.getFueltype());
        car.setFueltype(carEntity.getFueltype());

        return car;
    }

    public Car toModel(CarRequest model){


        Car car = new Car();

        car.setId(model.getId());
        car.setBrand(brandMapper.toBrandModel(model.getBrandRequest()));
        car.setModel(model.getModel());
        car.setYear(model.getYear());
        car.setColour(model.getColour());
        car.setMillage(model.getMillage());
        car.setDescription(model.getDescription());
        car.setFueltype(model.getFueltype());

        return car;
    }
}
