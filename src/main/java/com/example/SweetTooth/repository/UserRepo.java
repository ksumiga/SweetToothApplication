/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.SweetTooth.repository;

import com.example.SweetTooth.model.User;
import com.example.SweetTooth.model.UserCredentials;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lenovo
 */
public interface UserRepo extends JpaRepository<User, Integer>{
    
    Optional<User> findUserByEmail(String email);
    
}
