package com.jpceron.CarRegistry.repository;

import com.jpceron.CarRegistry.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity,Integer> {

    BrandEntity findByName(String name);

}
