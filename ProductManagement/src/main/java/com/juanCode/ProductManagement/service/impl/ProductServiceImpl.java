package com.juanCode.ProductManagement.service.impl;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import com.juanCode.ProductManagement.repository.ProductRepository;
import com.juanCode.ProductManagement.service.ProductService;
import com.juanCode.ProductManagement.service.converters.ProductConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    private ProductConverter productConverter = new ProductConverter();


    @Override
    public Product getProductById(int id) {

        log.info("Retiving product: {}",id);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        return optionalProductEntity.map(productEntity -> productConverter.toProduct(productEntity)).orElse(null);

    }

    @Override
    @Async
    public CompletableFuture<List<ProductEntity>> findAll() {
        long startTieme= System.currentTimeMillis();
        List<ProductEntity>producsList = productRepository.findAll();

        long endTime = System.currentTimeMillis();
        log.info("Total tiem: "+(endTime-startTieme));

        return CompletableFuture.completedFuture(producsList);

    }

    @Override
    @Async
    public CompletableFuture<List<ProductEntity>> saveAll(List<ProductEntity> productEntityList) {

        long startTieme= System.currentTimeMillis();
       List<ProductEntity> list = productRepository.saveAll(productEntityList);

        long endTime = System.currentTimeMillis();
        log.info("Total time: "+(endTime-startTieme));

        return CompletableFuture.completedFuture(list);
    }


    @Override
    public Product saveProduct(Product productRequest) {
        log.info("Adding product .........");

        ProductEntity productEntity = productConverter.toEntity(productRequest);
        productRepository.save(productEntity);

        return productConverter.toProduct(productEntity);
    }



    @Override
    public void deleteById(int id) {
        log.info("Delete product id: {} ",id);
        productRepository.deleteById(id);
    }

    @Override
    public Product updateById(int id, Product productRequest) {

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        if(optionalProductEntity.isPresent()){
            ProductEntity entity = productConverter.toEntity(productRequest);
            entity.setId(id);

            return productConverter.toProduct(productRepository.save(entity));
        }

        return null;
    }




}
