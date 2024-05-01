package com.jpceron.CarRegistry.service;

import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.CarEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    Car getCarById(int id);
    void delete(int id);
    Car updateById(int id, Car carRequest);
    Car saveCar(Car carRequest);

    CompletableFuture< List<Car>> getAllCars();

    String carsCsv();
    List<CarEntity> uploadCars(MultipartFile file);

}
