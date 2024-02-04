/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.SweetTooth.repository;

import com.example.SweetTooth.model.PurchaseHistory;
import com.example.SweetTooth.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lenovo
 */
public interface PurchaseHistoryRepo extends JpaRepository<PurchaseHistory, Long> {

     PurchaseHistory save(PurchaseHistory purchaseHistory);
     List<PurchaseHistory> findAll();
}
