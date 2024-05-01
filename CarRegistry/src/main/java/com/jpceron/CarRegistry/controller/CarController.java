package com.jpceron.CarRegistry.controller;

import com.jpceron.CarRegistry.controller.dtos.BrandRequest;
import com.jpceron.CarRegistry.controller.dtos.BrandResponse;
import com.jpceron.CarRegistry.controller.dtos.CarRequest;
import com.jpceron.CarRegistry.controller.dtos.CarResponse;
import com.jpceron.CarRegistry.controller.mapper.BrandMapper;
import com.jpceron.CarRegistry.controller.mapper.CarMapper;
import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.BrandEntity;
import com.jpceron.CarRegistry.entity.CarEntity;
import com.jpceron.CarRegistry.service.BrandService;
import com.jpceron.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/carservice")
public class CarController {

    @Autowired
    private BrandService brandService;
    private BrandMapper brandMapper = new BrandMapper();


    @Autowired
    private CarService carService;

    private CarMapper carMapper = new CarMapper();


    //----------------------------------------------------------------------------------------

    @PostMapping("/addCar")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest) {
        log.info("CarRequestBody:" + carRequest.toString());


        try {
            carService.saveCar(carMapper.toModel(carRequest));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("No se puede guardar el coche");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteCar/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCarById(@PathVariable int id) {
        log.info("Retriving Car info");
        try {
            carService.delete(id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return null;
        }


    }

    @GetMapping("/car/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getCarId(@PathVariable int id) {
        log.info("Retriving car info");
        try {
            return ResponseEntity.ok(carService.getCarById(id));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateCar/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<CarEntity> updateCarById(@PathVariable int id, @RequestBody CarRequest carRequest) {

        try {
            carService.updateById(id, carMapper.toModel(carRequest));

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<?> getCars() {
        log.info("Retriving cars info");
        try {
            CompletableFuture<List<Car>> cars = carService.getAllCars();
            List<CarResponse> response = new ArrayList<>();
            cars.get().forEach(car -> {
                response.add(carMapper.toCarResponse(car));
            });
            return CompletableFuture.completedFuture(response);

        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }

    }


//------------------------------------------------------------------------------------------------


    @PostMapping("/addBrand")
    @PreAuthorize("hasRole('VENDOR)")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest) {
        log.info("brandRequest: " + brandRequest.getName());
        Brand b = new Brand();
        b.setName(brandMapper.toBrandModel(brandRequest).getName());
        log.info("b name: " + b.getName());

        try {
            brandService.saveBrand(brandMapper.toBrandModel(brandRequest));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/brand/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getBrandId(@PathVariable int id) {
        log.info("Retriving brand info");
        try {
            return ResponseEntity.ok(brandService.getBrandById(id));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/updateBrand/{id}")
    @PreAuthorize("hasRole('VENDOR)")
    public ResponseEntity<BrandEntity> updateById(@PathVariable int id, @RequestBody BrandRequest brandRequest) {

        try {
            brandService.updateBrandByid(id, brandMapper.toBrandModel(brandRequest));

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteBrand/{id}")
    @PreAuthorize("hasRole('VENDOR)")
    public ResponseEntity<?> deleteBrandById(@PathVariable int id) {
        log.info("Retriving Car info");
        try {
            brandService.deleteBrand(id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/brands")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<?> getBrands() {
        log.info("Retriving brands info");
        try {

            CompletableFuture<List<Brand>> brands = brandService.getAllBrands();
            List<BrandResponse> response = new ArrayList<>();
            brands.get().forEach(brand -> {
                response.add(brandMapper.toResponse(brand));
            });
            return CompletableFuture.completedFuture(response);

        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }
    }


//-----------------------------------------------------------------------------------------------


    @GetMapping(value = "/downloadCsv")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> downloadCsv() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "cars.csv");

        byte[] csvBytes = carService.carsCsv().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(@RequestParam(value = "carFile") MultipartFile file) {

        if (file.isEmpty()) {

            log.error("The file it`s empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } else if (file.getOriginalFilename().contains(".csv")) {
            carService.uploadCars(file);
            return ResponseEntity.ok("File succesfully upload");

        }
        log.error("The file it`s CSV");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file it´s not CSV");
    }

}
