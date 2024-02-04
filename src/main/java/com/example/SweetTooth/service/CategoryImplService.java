/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.service;

import java.util.List;
import com.example.SweetTooth.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.SweetTooth.repository.CategoryRepo;
import java.util.Optional;
import org.springframework.ui.Model;

/**
 *
 * @author Lenovo
 */

@Service
public class CategoryImplService {   
    
    @Autowired
    CategoryRepo categoryRepo;
    
    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }
    
    public void addCategory(Category category){
        categoryRepo.save(category);
    }
    
    public void removeCategoryById(int id){
        categoryRepo.deleteById(id);
    }
   
    public Optional<Category> getCAtegoryById(int id){
        return categoryRepo.findById(id);
    }
}
 