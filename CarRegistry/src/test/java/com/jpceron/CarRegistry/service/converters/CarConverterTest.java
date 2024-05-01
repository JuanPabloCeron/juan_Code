package com.jpceron.CarRegistry.service.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.BrandEntity;
import com.jpceron.CarRegistry.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarConverterTest {


    @Mock
    BrandConverter brandConverter;

    @InjectMocks
    CarConverter converter;

    CarEntity carEntity = new CarEntity();
    Car car = new Car();
    BrandEntity brandEntity = new BrandEntity();
    Brand brand = new Brand();

    @Test
    void test_toCar(){

        carEntity.setId(1);
        car.setId(carEntity.getId());

        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);
        car.setBrand(brandConverter.toBrand(brandEntity));

        carEntity.setModel("Focus");
        car.setModel(carEntity.getModel());

        carEntity.setYear(2024);
        car.setYear(carEntity.getYear());

        carEntity.setColour("Black");
        car.setColour(carEntity.getColour());

        carEntity.setMillage(2);
        car.setMillage(carEntity.getMillage());

        carEntity.setDescription("Sport");
        car.setDescription(carEntity.getDescription());


        Car resault = converter.toCar(carEntity);

        assertEquals(resault,converter.toCar(carEntity) );

    }
}
