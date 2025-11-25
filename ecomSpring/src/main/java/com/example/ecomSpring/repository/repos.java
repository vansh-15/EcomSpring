package com.example.ecomSpring.repository;


import com.example.ecomSpring.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repos extends JpaRepository<ProductModel,Integer> {

    // we just declare method and use dsl (domain specific language)
    // using jpql format
    @Query("SELECT p from ProductModel p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductModel> searchProducts(String keyword);

}
