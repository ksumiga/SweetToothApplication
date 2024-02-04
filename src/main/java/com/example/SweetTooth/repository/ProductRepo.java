/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.SweetTooth.repository;

import com.example.SweetTooth.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lenovo
 */
public interface ProductRepo extends JpaRepository<Product, Long> {
    
    public List<Product> findAllByCategoryId(int id);
    
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.name = :productName AND (:productId IS NULL OR p.id <> :productId)")
    boolean existsByNameAndIdNot(@Param("productName") String productName, @Param("productId") Long productId);
}
