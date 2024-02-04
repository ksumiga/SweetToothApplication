/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.dto;
import lombok.Data;

/**
 *
 * @author Lenovo
 */
@Data
public class ProductDTO {
    
    private Long id;
    private String name;
    private int categoryId;    
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
