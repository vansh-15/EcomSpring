package com.example.ecomSpring.service;

import com.example.ecomSpring.model.ProductModel;
import com.example.ecomSpring.repository.repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private repos repo;

    public List<ProductModel> getAllProducts() {
        return repo.findAll();
    }

    public ProductModel getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public ProductModel addProduct(ProductModel product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }

    public ProductModel updateProduct(ProductModel product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<ProductModel> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
