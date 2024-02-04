/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.SweetTooth.repository;

import com.example.SweetTooth.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Lenovo
 */
public interface CategoryRepo extends JpaRepository<Category, Integer>{
    
}
