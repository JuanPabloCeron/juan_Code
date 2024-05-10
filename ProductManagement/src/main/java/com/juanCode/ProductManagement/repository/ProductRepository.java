package com.juanCode.ProductManagement.repository;

import com.juanCode.ProductManagement.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository <ProductEntity,Integer> {}
