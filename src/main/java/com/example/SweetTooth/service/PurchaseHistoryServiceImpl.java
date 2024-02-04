/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.service;

import com.example.SweetTooth.model.PurchaseHistory;
import com.example.SweetTooth.repository.PurchaseHistoryRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class PurchaseHistoryServiceImpl {

   @Autowired
    private PurchaseHistoryRepo purchaseHistoryRepo;
        
    public PurchaseHistory savePurchaseHistory(PurchaseHistory purchaseHistory) {
        return purchaseHistoryRepo.save(purchaseHistory);
    }

    public List<PurchaseHistory> findAllPurchases() {
        return purchaseHistoryRepo.findAll();
    }
}
