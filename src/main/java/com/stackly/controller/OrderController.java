package com.stackly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackly.dto.OrderRequest;
import com.stackly.entity.Order;
import com.stackly.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody(required = false) OrderRequest request,HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loggedInUser");
        if (userId == null) return ResponseEntity.status(401).body("Please Login");
        
        try {
        Order finalOrder = orderService.placeOrder(userId,request);
        
        String response = String.format("Order #%d successful! Your items will be delevired to : %s."
        		+ "Expected deleviry : %s(within 5 days).COntact : %s ",
        		finalOrder.getId(),
        		finalOrder.getShippingaddress(),
        		finalOrder.getDeliveryDate(),
        		finalOrder.getShippingPhone());
        return ResponseEntity.ok(response);
        }
        catch(Exception e) {
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}