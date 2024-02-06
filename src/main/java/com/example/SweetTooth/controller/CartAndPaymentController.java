/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.controller;

import com.example.SweetTooth.config.PayPalPaymentIntent;
import com.example.SweetTooth.config.PayPalPaymentMethod;
import com.example.SweetTooth.global.Global;
import com.example.SweetTooth.model.PayPalPaymentEvent;
import com.example.SweetTooth.model.Product;
import com.example.SweetTooth.model.PurchaseHistory;
import com.example.SweetTooth.model.User;
import com.example.SweetTooth.repository.UserRepo;
import com.example.SweetTooth.service.PayPalServiceImpl;
import com.example.SweetTooth.service.ProductImplService;
import com.example.SweetTooth.service.PurchaseHistoryServiceImpl;
import com.example.SweetTooth.service.UserCredentialsImplService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lenovo
 */
@Controller
public class CartAndPaymentController {
    
    @Autowired
    ProductImplService productImplService;
    
    @Autowired
    PayPalServiceImpl paypalService;
    
    @Autowired
    UserCredentialsImplService userService;
    
    @Autowired
    PurchaseHistoryServiceImpl historyService;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    
    private String cancelUrl = "http://localhost:8080/payNotApproved";
    private String successUrl = "http://localhost:8080/paySuccess";
    
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        Global.cart.add(productImplService.getProductById(id).get());
        return "redirect:/shop";
    }
    
    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", Global.cart.size());
        model.addAttribute("total", Global.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", Global.cart);
        return "cart";
    }
         
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        Global.cart.remove(index);
        return "redirect:/cart";
    }
    
    @GetMapping("/checkout")
    public String checkout(Model model){
        
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "Guest");
        }

        model.addAttribute("total", Global.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
 
    @PostMapping("/initiatePayPalPayment")
    public String initiatePayPalPaymentPost(HttpServletRequest request) {
      
       try {

        double total = Global.cart.stream().mapToDouble(Product::getPrice).sum();
        Payment payment = paypalService.createPayment(
                total,
                "EUR",
                PayPalPaymentMethod.paypal,
                PayPalPaymentIntent.sale,
                "Payment description",
                cancelUrl,
                successUrl);

        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return "redirect:" + links.getHref();
            }
        }
    } catch (PayPalRESTException e) {
      
    }
        return "redirect:/checkout";
    }
    
    @GetMapping("/paySuccess")
    public String showPaymentSuccessPage(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session) {
       
        try {
           
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        // Getting logged in username which is email address in this instance for creating purchase history 
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
            
        } else if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            username = oidcUser.getEmail();
            
        } else {
            username = "Guest";
        }                                

        Payment payment = paypalService.executePayment(paymentId, payerId);

        if (payment.getState().equals("approved")) {
            Optional<User> optionalUser = userRepo.findUserByEmail(username);
            User user = optionalUser.orElseGet(User::new);
            user.setEmail(username);

        List<Product> products = productImplService.getProductsByIds(Global.cart.stream().map(Product::getId)
                .collect(Collectors.toList()));

        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser(user);
        products.forEach(purchaseHistory::addPurchasedItem);
        purchaseHistory.setTotalAmount(products.stream().mapToDouble(Product::getPrice).sum());
        purchaseHistory.setPurchaseDate(LocalDateTime.now());
        historyService.savePurchaseHistory(purchaseHistory);

        Global.cart.clear();

        eventPublisher.publishEvent(new PayPalPaymentEvent(this, "approved"));
            return "paySuccess";
        } 
        else {
        eventPublisher.publishEvent(new PayPalPaymentEvent(this, "not_approved"));
        }
        } 
        catch (PayPalRESTException e) {
      
        }
            return "redirect:/checkout";
    }

    @GetMapping("/payNotApproved")
    public String showPaymentNotApprovedPage() {
        
        eventPublisher.publishEvent(new PayPalPaymentEvent(this, "not_approved"));
        return "payNotApproved";
    }
    
   @GetMapping("/history")
    public String showPurchaseHistory(Model model) {
    
    List<PurchaseHistory> purchaseHistoryList = historyService.findAllPurchases();
    Map<String, List<PurchaseHistory>> groupedHistory = purchaseHistoryList.stream()
            .collect(Collectors.groupingBy(purchase -> purchase.getUser().getEmail())); //display purchase details of each purchase made for a user email

    Map<String, Double> userTotalAmounts = groupedHistory.entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().stream().mapToDouble(PurchaseHistory::getTotalAmount).sum() //total sum of all purchases made for a single user
            ));

    model.addAttribute("groupedHistory", groupedHistory);
    model.addAttribute("userTotalAmounts", userTotalAmounts);

    return "history";
    }
}
