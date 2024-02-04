/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Lenovo
 */
public class ManualPasswordEncoder {
 
     public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
