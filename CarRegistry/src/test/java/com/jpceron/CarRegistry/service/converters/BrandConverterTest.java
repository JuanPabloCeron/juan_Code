package com.jpceron.CarRegistry.service.converters;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.entity.BrandEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandConverterTest {

    @InjectMocks
    BrandConverter brandConverter;

    BrandEntity brandEntity = new BrandEntity();
    Brand brand = new Brand();

    @Test
    void test_toBrand(){


        brandEntity.setId(1);
        brand.setId(brandEntity.getId());
        brandEntity.setName("Ford");
        brand.setName(brandEntity.getName());
        brandEntity.setCountry("EEUU");
        brand.setCountry(brandEntity.getCountry());
        brandEntity.setWarranty(2);
        brand.setWarranty(brandEntity.getWarranty());

        Brand resault = brandConverter.toBrand(brandEntity);

        assertEquals(resault,brandConverter.toBrand(brandEntity));

    }

    @Test
    void test_toEntity(){

        brand.setId(1);
        brandEntity.setId(brand.getId());
        brand.setName("Ford");
        brandEntity.setName(brand.getName());
        brand.setCountry("EEUU");
        brandEntity.setCountry(brand.getCountry());
        brand.setWarranty(3);
        brandEntity.setWarranty(brand.getWarranty());

        BrandEntity resault = brandConverter.toEntity(brand);

        assertEquals(resault,brandConverter.toEntity(brand));


    }

}
