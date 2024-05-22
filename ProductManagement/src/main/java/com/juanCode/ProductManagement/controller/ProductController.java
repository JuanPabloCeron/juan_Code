package com.juanCode.ProductManagement.controller;

import com.juanCode.ProductManagement.domain.Product;
import com.juanCode.ProductManagement.entity.ProductEntity;
import com.juanCode.ProductManagement.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        log.info("Product RequestBody: "+product.getId());

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

    @GetMapping("/products")
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
  //-----------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file){

        if(file.isEmpty()){
            log.error("The file is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        log.info("File it`s {}",file.getOriginalFilename());

        return ResponseEntity.ok("File succesfull upload");
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<?> downloadFile() throws IOException {

        MediaType contentType = MediaType.IMAGE_PNG;
        InputStream in = new FileInputStream("src/main/resources/smarthPhone.png");

        return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(in));
    }

    @PostMapping("productImage/{id}/add")
    public ResponseEntity<?> addImageProduct(@PathVariable int id, @RequestParam("imageFile") MultipartFile imageFile){
        try{

            productService.addProductImage(id,imageFile);
            return ResponseEntity.ok("Image succesfully saved");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/productImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id){
        try{
            byte[] imageByte = productService.getProductImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageByte,headers,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping(value = "/uploadCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(@RequestParam(value = "file") MultipartFile file){
        if(file.isEmpty()){
            log.error("The file is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(file.getOriginalFilename().contains(".csv")){

            log.info("Filename it`s {}",file.getOriginalFilename());
            productService.uploadProducts(file);
            return ResponseEntity.ok("File succesfully uploaded");

        }
        log.error("The file it´s not a CSV");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file it´s not CSV");

    }

    @GetMapping("/downloadFileCSV")
    public ResponseEntity<?> downloadFileCSV() throws IOException {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment","products.csv");

        byte[] csvBytes =  productService.productCsv().getBytes();


        return new ResponseEntity<>(csvBytes, headers,HttpStatus.OK);
    }




}





























