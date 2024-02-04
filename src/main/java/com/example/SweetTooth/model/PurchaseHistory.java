/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Lenovo
 */
@Entity
public class PurchaseHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") 
    private User user;
   
   @OneToMany(mappedBy = "purchaseHistory", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Product> purchasedItems;
   
   private double totalAmount;

    private LocalDateTime purchaseDate;

    public PurchaseHistory(User user, List<Product> purchasedItems, double totalAmount, LocalDateTime purchaseDate) {
        this.user = user;
        this.purchasedItems = purchasedItems;
        this.totalAmount = totalAmount;
        this.purchaseDate = purchaseDate;
    }

    public PurchaseHistory() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Product> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }
  
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
   public void addPurchasedItem(Product product) {
    if (purchasedItems == null) {
        purchasedItems = new ArrayList<>();
    }
    product.setPurchaseHistory(this);
    purchasedItems.add(product); 
   }
}
    

