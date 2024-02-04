/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.controller;

import com.example.SweetTooth.global.Global;
import com.example.SweetTooth.model.Role;
import com.example.SweetTooth.model.User;
import com.example.SweetTooth.repository.RoleRepo;
import com.example.SweetTooth.repository.UserRepo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Lenovo
 */
@Controller
public class LoginController {
    
    @Autowired
    private BCryptPasswordEncoder bCPE;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    RoleRepo roleRepo;
    
    @GetMapping("/login")
    public String login(){
        Global.cart.clear();
        return "login";
    }
    
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user")User user, HttpServletRequest request) throws ServletException{
            String password = user.getPassword();
            user.setPassword(bCPE.encode(password));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepo.findById(2).get());
            user.setRoles(roles);
            userRepo.save(user);
            request.login(user.getEmail(), password);
            return "redirect:/";
    }
    
}
