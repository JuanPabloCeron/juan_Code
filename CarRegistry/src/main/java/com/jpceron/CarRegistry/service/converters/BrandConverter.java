package com.jpceron.CarRegistry.service.converters;

import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.entity.BrandEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandConverter {

    public Brand toBrand (BrandEntity brandEntity){
        Brand brand = new Brand();

        brand.setId(brandEntity.getId());
        brand.setName(brandEntity.getName());
        brand.setCountry(brandEntity.getCountry());
        brand.setWarranty(brandEntity.getWarranty());

        return brand;
    }

    public BrandEntity toEntity(Brand brand){
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setId(brand.getId());
        brandEntity.setName(brand.getName());
        brandEntity.setCountry(brand.getCountry());
        brandEntity.setWarranty(brand.getWarranty());

        return brandEntity;
    }


}
