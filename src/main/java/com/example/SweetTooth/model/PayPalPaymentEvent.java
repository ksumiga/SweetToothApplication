/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.model;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Lenovo
 */

public class PayPalPaymentEvent extends ApplicationEvent {
    
     private String paymentState;

    public PayPalPaymentEvent(Object source, String paymentState) {
        super(source);
        this.paymentState = paymentState;
    }

    public String getPaymentState() {
        return paymentState;
    }
    
}
