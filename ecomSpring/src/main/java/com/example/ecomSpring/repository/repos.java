package com.example.ecomSpring.repository;


import com.example.ecomSpring.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repos extends JpaRepository<ProductModel,Integer> {



}
