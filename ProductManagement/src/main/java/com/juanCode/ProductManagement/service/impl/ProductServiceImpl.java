package com.juanCode.ProductManagement.service.impl;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import com.juanCode.ProductManagement.repository.ProductRepository;
import com.juanCode.ProductManagement.service.ProductService;
import com.juanCode.ProductManagement.service.converters.ProductConverter;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    private ProductConverter productConverter = new ProductConverter();

    private final String[] HEADERS = {"id","name","description","price","stock","brand"};


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

    @Override
    public void addProductImage(int id, MultipartFile imageFile) throws IOException {
        ProductEntity entity = productRepository.findById(id).orElseThrow(RuntimeException :: new);
        log.info("Saving product image... ");
    entity.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
    productRepository.save(entity);
    }

    @Override
    public byte[] getProductImage(int id) {
        ProductEntity entity = productRepository.findById(id).orElseThrow(RuntimeException:: new);
        return Base64.getDecoder().decode(entity.getImage());
    }

    @Override
    public List<ProductEntity> uploadProducts(MultipartFile file) {

        List<ProductEntity>productList = new ArrayList<>();

    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
         CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {


        Iterable<CSVRecord> csvRecord = csvParser.getRecords();

        for(CSVRecord record : csvRecord){
            ProductEntity product = new ProductEntity();

            product.setId(Integer.parseInt(record.get("id")));
            product.setName(record.get("name"));
            product.setDescription(record.get("description"));
            product.setPrice(Double.parseDouble(record.get("price")));
            product.setStock(Integer.parseInt(record.get("stock")));
            product.setBrand(record.get("brand"));

            productList.add(product);

        }

       productList = productRepository.saveAll(productList);

    } catch (IOException e) {
        log.error("Failed to load users");
        throw new RuntimeException(e);
    }

    return productList;
    }

    @Override
    public String productCsv() {

        List<ProductEntity> productEntityList = productRepository.findAll();
        StringBuilder csvContent = new StringBuilder();
        Arrays.stream(HEADERS).forEach(header -> csvContent.append(header).append(","));
        csvContent.setLength(csvContent.length() - 1); // Remove the last comma
        csvContent.append("\n");
        for (ProductEntity product : productEntityList){

            csvContent.append(product.getId()).append(",")
                    .append(product.getName()).append(",")
                    .append(product.getDescription()).append(",")
                    .append(product.getPrice()).append(",")
                    .append(product.getStock()).append(",")
                    .append(product.getBrand()).append("\n");

        }


        return csvContent.toString();
    }


}
