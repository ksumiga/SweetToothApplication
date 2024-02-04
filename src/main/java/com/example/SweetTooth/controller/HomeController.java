/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.controller;

import com.example.SweetTooth.global.Global;
import com.example.SweetTooth.service.CategoryImplService;
import com.example.SweetTooth.service.ProductImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Lenovo
 */
@Controller
public class HomeController {
    
    @Autowired
    CategoryImplService categoryImplService;
    
    @Autowired
    ProductImplService productImplService;   
    
    @GetMapping({"/", "/home"})
    public String home(Model model){
        
        model.addAttribute("cartCount", Global.cart.size());
        return "index";
    }
    
    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryImplService.getAllCategories());
        model.addAttribute("products", productImplService.getAllProducts());
        model.addAttribute("cartCount", Global.cart.size());
        return "shop";
    }
     
    @GetMapping("/shop/category/{id}")
    public String shopFromCategory(Model model, @PathVariable int id){
        model.addAttribute("categories", categoryImplService.getAllCategories());
        model.addAttribute("cartCount", Global.cart.size());
        model.addAttribute("products", productImplService.getProductsInCategory(id));
        
        return "shop";
    }
    
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productImplService.getProductById(id).get());
        model.addAttribute("cartCount", Global.cart.size());
        return "viewproduct";
    }
}
