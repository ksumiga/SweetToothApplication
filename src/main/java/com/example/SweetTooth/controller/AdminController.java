
package com.example.SweetTooth.controller;


import com.example.SweetTooth.dto.ProductDTO;
import com.example.SweetTooth.model.Category;
import com.example.SweetTooth.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.SweetTooth.service.CategoryImplService;
import com.example.SweetTooth.service.ProductImplService;
import com.jayway.jsonpath.internal.Path;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
@Controller
public class AdminController {
    
    public static String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator +
        "main" + File.separator + "resources" + File.separator + "static" + File.separator + "ProductImages";
    @Autowired
    CategoryImplService categoryImplService; 
    
    @Autowired
    ProductImplService productImplService;
        
    @GetMapping("/admin")
    public String adminHome(){
    
        return "adminHome";
    }
    
    @GetMapping("/admin/categories")
    public String getCat(Model model){ 
        model.addAttribute("categories", categoryImplService.getAllCategories());
        return "categories";
    }
     
    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }
    
    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute ("category") Category category){
        categoryImplService.addCategory(category);
        return "redirect:/admin/categories";
    } 
    
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryImplService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryImplService.getCAtegoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        else{
            return "404-not found!";
        }
    }
    
    @GetMapping("/admin/products")
    public String products(Model model){
        model.addAttribute("products", productImplService.getAllProducts());
        return "products";
    }
    
    @GetMapping("/admin/products/add")
    public String productAddGet(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryImplService.getAllCategories());
        return "productsAdd";
    }
    
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("ProductDTO")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException{
       
        
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryImplService.getCAtegoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setPrice(productDTO.getPrice());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            java.nio.file.Path fileNamePath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNamePath, file.getBytes());
        }
        else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);        
             
        productImplService.addProduct(product); 
            
        return "redirect:/admin/products";      
       }        
           
       
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProd(@PathVariable Long id){
        productImplService.removeProductById(id);
        return "redirect:/admin/products";
    }
    
    @GetMapping("/admin/product/update/{id}")
    public String updateProd(@PathVariable Long id, Model model){
        
        Product product = productImplService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        
        model.addAttribute("categories", categoryImplService.getAllCategories());
        model.addAttribute("productDTO", productDTO);      
            
        return "productsAdd";
    }
   
}
