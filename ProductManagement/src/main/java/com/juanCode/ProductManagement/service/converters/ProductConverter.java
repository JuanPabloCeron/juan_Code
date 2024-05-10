package com.juanCode.ProductManagement.service.converters;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductConverter {

    public Product toProduct (ProductEntity productEntity){

        Product product = new Product();

        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setBrand(productEntity.getBrand());
        product.setPrice(productEntity.getPrice());
        product.setStock(productEntity.getStock());

        return product;
    }

    public ProductEntity toEntity(Product product){

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setBrand(product.getBrand());
        productEntity.setPrice(product.getPrice());
        productEntity.setStock(product.getStock());

        return productEntity;
    }
}
