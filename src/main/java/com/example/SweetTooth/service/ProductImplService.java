/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.service;

import com.example.SweetTooth.model.Category;
import com.example.SweetTooth.model.Product;
import com.example.SweetTooth.repository.ProductRepo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class ProductImplService {
    
    @Autowired
    ProductRepo productRepo;
    
       
     public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
    
    public void addProduct(Product product){
          productRepo.save(product);
    }
    
    public void removeProductById(Long id){
        productRepo.deleteById(id);
    }
    
    public Optional<Product> getProductById(Long id){
       return productRepo.findById(id);
    }
    
public List<Product> getProductsByIds(List<Long> ids) {
    return productRepo.findAllById(ids);
}
    
    public List<Product> getProductsInCategory(int id){
        return productRepo.findAllByCategoryId(id);
    }
  
}
