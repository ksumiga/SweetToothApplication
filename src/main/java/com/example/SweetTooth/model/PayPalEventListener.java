/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.model;

import com.example.SweetTooth.service.PayPalRedirectService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class PayPalEventListener {
    
 @Autowired
    private PayPalRedirectService redirectService;

    @EventListener
    public void handlePayPalPaymentEvent(PayPalPaymentEvent event) {
        
        String paymentState = event.getPaymentState();

        try {
            if ("approved".equals(paymentState)) {
               
                redirectService.redirectToSuccessPage();
            } else {
                
                redirectService.redirectToPaymentNotApprovedPage();
            }
        } catch (IOException e) {
           
        }
    }
}
