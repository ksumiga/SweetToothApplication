/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Builder.Default;
import lombok.Data;
import static org.hibernate.engine.internal.Cascade.cascade;
import static org.thymeleaf.spring5.util.FieldUtils.errors;

/**
 *
 * @author Lenovo
 */
@Entity
@Data
@Table(name = "users")
public class User {

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User(){}
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotEmpty
    @Column(nullable = false)
    private String firstName;
    
    
    private String lastName;
    
    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;
    
    @Column(nullable = true)
    private String password;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
           
    )
       
    private List<Role> roles;
}
