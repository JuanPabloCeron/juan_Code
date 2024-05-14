package com.jpceron.CarRegistry.service.impl;
import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.entity.BrandEntity;
import com.jpceron.CarRegistry.repository.BrandRepository;
import com.jpceron.CarRegistry.service.BrandService;
import com.jpceron.CarRegistry.service.converters.BrandConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    BrandConverter brandConverter = new BrandConverter();

   @Override
    public Brand saveBrand(Brand brandRequest){

        log.info("Adding car to Database......");
        BrandEntity entity = brandConverter.toEntity(brandRequest);

        return brandConverter.toBrand(brandRepository.save(entity));
    }

    @Override
    public Brand getBrandById(int id) {
        log.info("Retrieving car with id {}",id);
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);

        if(brandEntityOptional.isPresent()){
            return brandConverter.toBrand(brandEntityOptional.get());
        }else {
            return null;
        }
    }

    @Override
    public Brand updateBrandByid(int id, Brand brandRequest){

        log.info("Updating brand with id {}",id);
        Optional<BrandEntity> brandOptional = brandRepository.findById(id);

        if(brandOptional.isPresent()){


            BrandEntity entity = brandConverter.toEntity(brandRequest);
            entity.setId(id);

            return brandConverter.toBrand(brandRepository.save(entity));
        }else {
            return null;
        }
    }

    @Override
    public  void deleteBrand(int id){

        brandRepository.deleteById(id);

    }

//------------------------------------------------------------------------------
    @Async
    @Override
    public  CompletableFuture< List<Brand>> getAllBrands(){

        List<BrandEntity> brandList = brandRepository.findAll();

        List<Brand> brands = new ArrayList<>();

        brandList.forEach(brand -> brands.add(brandConverter.toBrand(brand)));

        return CompletableFuture.completedFuture(brands);
    }




}
