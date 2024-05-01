package com.jpceron.CarRegistry.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.CarEntity;
import com.jpceron.CarRegistry.repository.CarRepository;
import com.jpceron.CarRegistry.service.converters.CarConverter;
import com.jpceron.CarRegistry.service.impl.CarServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;


    CarEntity carEntity = new CarEntity();

    Car car = new Car();

    int carId = 16;

    @Test
    void test_getCarById(){
        // Mocking repository response



        carEntity.setId(16);

        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));

        // Mocking converter response
        when(carConverter.toCar(carEntity)).thenReturn(car);

        // Calling the method under test
        Car result = carService.getCarById(carId);

        // Verifying interactions and assertions
        verify(carRepository).findById(carId);
        verify(carConverter).toCar(carEntity);
        assertEquals(car, result);

    }

    //----------------------------------------------------------------------------------

    @Test
    void test_saveCar(){

        // Create a sample Car object for testing

        // Assuming carConverter returns a CarEntity based on the input Car

        when(carConverter.carToEntity(car)).thenReturn(carEntity);
        // Assuming carRepository returns the saved CarEntity
        when(carRepository.save(carEntity)).thenReturn(carEntity);
        // Assuming carConverter returns a Car based on the saved CarEntity
        when(carConverter.toCar(carEntity)).thenReturn(car);

        // Call the method under test
        Car result = carService.saveCar(car);

        // Verify interactions and assertions

        verify(carRepository).save(carEntity);

        assertEquals(car, result);
    }

    //--------------------------------------------------------------------------------------

    @Test
    void test_delete() {
        carRepository.deleteById(carId);
        carRepository.deleteById(0);

        verify(carRepository).deleteById(carId);
        verify(carRepository).deleteById(0);
    }

    //--------------------------------------------------------------------------------------
    @Test
    void test_updateCarById() {


        carEntity.setId(carId);


        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));


        when(carConverter.carToEntity(car)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);
        when(carConverter.toCar(carEntity)).thenReturn(car);


        Car result = carService.updateById(carId, car);


        verify(carRepository).findById(carId);
        verify(carConverter).carToEntity(car);
        verify(carRepository).save(carEntity);
        verify(carConverter).toCar(carEntity);
        assertEquals(car, result);
    }

}
















