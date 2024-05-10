package com.juanCode.ProductManagement.controller;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import com.juanCode.ProductManagement.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;



    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        log.info("Product RequestBody: "+product.getId().toString());

        try{

            productService.saveProduct(product);
            return  ResponseEntity.ok().build();

        }catch (Exception e){
            log.info("Error to safe the product !!");
            return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/addProducts")
    public CompletableFuture<?> addProducts(@RequestBody List<ProductEntity> productEntityList){

        return productService.saveAll(productEntityList).thenApply(ResponseEntity::ok);
    }

    //-----------------------------------------------------------------------------------------------------

    @GetMapping("/getProduct/{id}")
    public  ResponseEntity<?> getProduct(@PathVariable int id){

        try{
            if(productService.getProductById(id) == null){

                String mensaje = "The requested resource was not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
            return ResponseEntity.ok(productService.getProductById(id));
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProducts")
    public  CompletableFuture<?> getAllProducts(){

        try{
            return CompletableFuture.completedFuture(productService.findAll());
        }catch (Exception e){

      return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }
    }

    //---------------------------------------------------------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){

        try{
            if(productService.getProductById(id) == null){

                String mensaje = "The requested resource was not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
            productService.deleteById(id);
            return ResponseEntity.ok("Product id: "+id+" deleted succesfully");
        }catch (Exception e){

            return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping("/updateProduct/{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable int id,@RequestBody Product product) {
    try {
      if (productService.getProductById(id) == null) {

        String mensaje = "The requested resource was not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
      }
      return ResponseEntity.ok(productService.updateById(id,product));
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
