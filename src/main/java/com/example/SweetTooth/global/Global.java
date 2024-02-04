/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.global;

import com.example.SweetTooth.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Global {
    public static List<Product> cart;
    static{
        cart = new ArrayList<Product>();
    }
}
