package com.jpceron.CarRegistry.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jpceron.CarRegistry.domain.Brand;
import com.jpceron.CarRegistry.entity.BrandEntity;
import com.jpceron.CarRegistry.repository.BrandRepository;
import com.jpceron.CarRegistry.service.converters.BrandConverter;
import com.jpceron.CarRegistry.service.impl.BrandServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandConverter brandConverter;

    @InjectMocks
    private BrandServiceImpl brandService;

    BrandEntity brandEntity = new BrandEntity();
    Brand brand = new Brand();
    int id = 1;

    @Test
    void test_saveBrand() {

        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.saveBrand(brand);

        verify(brandConverter).toEntity(brand);
        verify(brandRepository).save(brandEntity);
        verify(brandConverter).toBrand(brandEntity);

        assertEquals(brand, result);
    }

    @Test
    void test_getBrandById(){

        brandEntity.setId(id);


        when(brandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.getBrandById(id);

        verify(brandRepository).findById(id);
        verify(brandConverter).toBrand(brandEntity);
        assertEquals(brand, result);

    }

    @Test
    void test_deleteBrand(){
        brandRepository.deleteById(id);

        verify(brandRepository).deleteById(id);
    }

    @Test
    void test_updateBrandByid(){
        brandEntity.setId(id);

        when(brandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand resault = brandService.updateBrandByid(id,brand);

        verify(brandRepository).findById(id);
        verify(brandConverter).toEntity(brand);
        verify(brandRepository).save(brandEntity);
        verify(brandConverter).toBrand(brandEntity);

        assertEquals(brand,resault);

    }

}

