package com.jpceron.CarRegistry.service;


import com.jpceron.CarRegistry.domain.Brand;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    Brand saveBrand(Brand brandRequest);
    Brand getBrandById(int id);
    Brand updateBrandByid(int id, Brand brandRequest);
    void deleteBrand(int id);

    CompletableFuture<List<Brand>> getAllBrands();


}
