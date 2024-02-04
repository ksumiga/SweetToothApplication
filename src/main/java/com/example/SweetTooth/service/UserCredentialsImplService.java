/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.service;

import com.example.SweetTooth.model.User;
import com.example.SweetTooth.model.UserCredentials;
import com.example.SweetTooth.repository.UserRepo;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class UserCredentialsImplService implements UserDetailsService{

     
    @Autowired
    UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Optional<User> user = userRepo.findUserByEmail(email);
            user.orElseThrow(()->new UsernameNotFoundException("User doesn't exist!"));
            return user.map(UserCredentials::new).get();
    }
    
    @Transactional
    public User saveUser(User user) {
        return userRepo.save(user);
    }

}
