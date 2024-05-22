package com.juanCode.ProductManagement.service;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface ProductService {

   Product getProductById(int id);
   CompletableFuture<List<ProductEntity>> findAll();

   Product saveProduct(Product productRequest);
   CompletableFuture<List<ProductEntity>> saveAll(List<ProductEntity> productEntityList);

   void deleteById(int id);
   Product updateById(int id, Product productRequest);


   void addProductImage(int id, MultipartFile imageFile) throws IOException;

    byte[] getProductImage(int id);

    List<ProductEntity> uploadProducts(MultipartFile file);

    String productCsv();
}
