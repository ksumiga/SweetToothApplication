/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.config;

import com.example.SweetTooth.model.Role;
import com.example.SweetTooth.model.User;
import com.example.SweetTooth.repository.RoleRepo;
import com.example.SweetTooth.repository.UserRepo;
import com.example.SweetTooth.service.UserCredentialsImplService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
        
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
   
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String email = token.getPrincipal().getAttributes().get("email").toString();
            if(userRepo.findUserByEmail(email).isPresent()){
            }
            else{
                User user = new User();
                user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                user.setEmail(email);
                List<Role> roles = new ArrayList<>();
                roles.add(roleRepo.findById(2).get());
                user.setRoles(roles);
                userRepo.save(user);
            }
            
            redirectStrategy.sendRedirect(request, response, "/");
    }
}
