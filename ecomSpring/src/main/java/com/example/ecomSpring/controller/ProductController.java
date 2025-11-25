package com.example.ecomSpring.controller;

import com.example.ecomSpring.model.ProductModel;
import com.example.ecomSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.ACCEPTED);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable int id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        ProductModel product=service.getById(productId);
        return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
    }

//    @PostMapping("/product")
//    public ResponseEntity<?> addProduct(@RequestPart ProductModel product, @RequestPart MultipartFile imageFile){
//        ProductModel savedProduct= null;
//        try {
//            savedProduct = service.addProduct(product,imageFile);
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
//    }

    @PostMapping(value = "/product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductModel product,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        System.out.println("=== Received product (server) ===");
        System.out.println(product);
        System.out.println("=== Received imageFile (server) ===");
        System.out.println(imageFile != null ? imageFile.getOriginalFilename() : "null");

        try {
            ProductModel savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("IO error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Server error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart ProductModel product, @RequestPart MultipartFile imageFile){
        ProductModel updatedProduct=null;
        try{
            updatedProduct=service.updateProduct(product,imageFile);
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        ProductModel product=service.getById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
    }

}
