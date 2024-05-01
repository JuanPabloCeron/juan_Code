package com.jpceron.CarRegistry.repository;

import com.jpceron.CarRegistry.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity,Integer> {

}
