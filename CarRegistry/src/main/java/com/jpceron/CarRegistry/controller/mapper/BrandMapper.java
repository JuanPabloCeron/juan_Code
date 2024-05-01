package com.jpceron.CarRegistry.controller.mapper;

import com.jpceron.CarRegistry.controller.dtos.BrandRequest;
import com.jpceron.CarRegistry.controller.dtos.BrandResponse;
import com.jpceron.CarRegistry.domain.Brand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandMapper {

    public BrandResponse toResponse(Brand brand){
        BrandResponse brandResponse = new BrandResponse();

        brandResponse.setId(brand.getId());
        brandResponse.setName(brand.getName());
        brandResponse.setCountry(brand.getCountry());
        brandResponse.setWarranty(brand.getWarranty());

        return brandResponse;
    }
    public Brand toBrandModel(BrandRequest model){

        Brand brand = new Brand();

        brand.setId(model.getId());
        brand.setName(model.getName());
        brand.setCountry(model.getCountry());
        brand.setWarranty(model.getWarranty());

        return brand;
    }

}
