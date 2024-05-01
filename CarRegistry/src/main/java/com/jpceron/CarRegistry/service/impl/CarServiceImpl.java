package com.jpceron.CarRegistry.service.impl;
import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.entity.CarEntity;
import com.jpceron.CarRegistry.repository.BrandRepository;
import com.jpceron.CarRegistry.repository.CarRepository;
import com.jpceron.CarRegistry.service.CarService;
import com.jpceron.CarRegistry.service.converters.CarConverter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.ClassUtils.getName;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Autowired
   private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    private CarConverter carConverter = new CarConverter();

    private final String[] HEADERS = {"id","model","colour","year"};

    @Override
    public Car getCarById(int id) {
        log.info("Retrieving car with id {}",id);
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);

        if(carEntityOptional.isPresent()){
            return carConverter.toCar(carEntityOptional.get());
        }else {
            return null;
        }
    }


   @Override
   public Car saveCar(Car carRequest){

        log.info("Adding car to Database......");

       CarEntity entity = carConverter.carToEntity(carRequest);

       carRepository.save(entity);
        return carConverter.toCar(entity);
   }



    @Override
    public Car updateById(int id, Car carRequest){

        log.info("Updating car with id {}",id);
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if(carOptional.isPresent()){


            CarEntity entity = carConverter.carToEntity(carRequest);
            entity.setId(id);

            return carConverter.toCar(carRepository.save(entity));
        }else {
            return null;
        }
    }

    @Override
    public  void delete(int id){

        log.info("Deliting car with id {}",id);
        carRepository.deleteById(id);

    }

//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------


    @Override
    @Async
    public  CompletableFuture< List<Car>> getAllCars(){
        List<CarEntity> carsList = carRepository.findAll();

        List<Car> cars = new ArrayList<>();
        carsList.forEach(car ->{
            cars.add(carConverter.toCar(car));
        });

        return CompletableFuture.completedFuture(cars);
    }

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------


    @Override
    public String carsCsv() {
        List<CarEntity> carList = carRepository.findAll();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(HEADERS);

        for(CarEntity car : carList){
            csvContent.append(car.getId()).append(",")
                    .append(car.getModel()).append(",")
                    .append(car.getColour()).append(",")
                    .append(car.getYear()).append("\n");
        }

        return csvContent.toString();
    }

    @Transactional
    @Override
    public List<CarEntity> uploadCars(MultipartFile file) {

        List<CarEntity> carList = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord record : csvRecords) {
                CarEntity car = new CarEntity();

                car.setModel(record.get("model"));
                car.setPrice(Double.parseDouble(record.get("price")));
                car.setColour(record.get("colour"));
                car.setDescription(record.get("description"));
                car.setFueltype(record.get("fueltype"));
                car.setMillage(Integer.parseInt(record.get("millage")));
                car.setYear(Integer.parseInt(record.get("year")));
                car.setBrand(brandRepository.findByName(getName("brand")));

                carList.add(car);
            }

            carList = carRepository.saveAll(carList);

        } catch (Exception e) {
            log.error("Failed to load or save users", e);
            throw new RuntimeException("Failed to load or save users", e);
        }

        return carList;
    }




}
