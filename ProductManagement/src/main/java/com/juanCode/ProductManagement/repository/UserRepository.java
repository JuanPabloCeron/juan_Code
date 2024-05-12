package com.juanCode.ProductManagement.repository;

import com.juanCode.ProductManagement.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);
}
