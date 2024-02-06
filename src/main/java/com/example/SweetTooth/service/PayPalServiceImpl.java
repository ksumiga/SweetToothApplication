/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.service;

import com.example.SweetTooth.config.PayPalPaymentIntent;
import com.example.SweetTooth.config.PayPalPaymentMethod;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class PayPalServiceImpl {
      
	@Autowired
	private APIContext apiContext;
        
        @Autowired
        private OAuthTokenCredential authTokenCredential;
        
        @Autowired
        private Map<String, String> paypalSdkConfig;

     public Payment createPayment(
           
            Double total,
            String currency,
            PayPalPaymentMethod method,
            PayPalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {

        paypalSdkConfig.put("requestId", generateUniqueRequestId());

        APIContext apiContext = new APIContext(authTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig);

        Amount amount = new Amount();
        amount.setCurrency(currency);

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        String formattedTotal = numberFormat.format(total);

        amount.setTotal(formattedTotal);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
      
        paypalSdkConfig.put("requestId", generateUniqueRequestId());

        APIContext apiContext = new APIContext(authTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig);

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecute);
    }
    
      private String generateUniqueRequestId() {
        return UUID.randomUUID().toString(); // Making a unique pay pal request Id for each purchase made by the same user, otherwise 
    }					     // pay pal refuses to process next purchase for logged on user because Request_Id is used
}					     // with the first purchase
