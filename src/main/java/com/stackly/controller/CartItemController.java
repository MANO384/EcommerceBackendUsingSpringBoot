package com.stackly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackly.dto.CartResponse;
import com.stackly.service.CartItemService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cartitem")
public class CartItemController {
	
    @Autowired
	private CartItemService cartItemService;
    
	@PostMapping("/add")
	public ResponseEntity<String> addTocart(@RequestParam Integer productId,
			@RequestParam Integer quantity,HttpSession session){
		
		Integer loggedInUserId = (Integer)session.getAttribute("loggedInUser");
		if(loggedInUserId == null) {
			return ResponseEntity.status(401).body("Pleasde Login First");
		}
		cartItemService.addProductToCart(loggedInUserId,productId,quantity);
		return ResponseEntity.ok("Product added to cart successfully!");
	}
	
	@GetMapping
	public ResponseEntity<?> showCartItem(HttpSession session){
		Integer loggedInUserId = (Integer)session.getAttribute("loggedInUser");
		
		if(loggedInUserId == null) {
			return ResponseEntity.status(401).body("Pleasde Login First");
		}
		
		CartResponse items = cartItemService.getCartDetails(loggedInUserId);
		
		return ResponseEntity.ok(items);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateQuantity(@RequestParam Integer productId,
			@RequestParam Integer quantity,HttpSession session){
		Integer loggedInUserId = (Integer)session.getAttribute("loggedInUser");
		if(loggedInUserId == null) {
			return ResponseEntity.status(401).body("Pleasde Login First");
		}
		cartItemService.updateQuantity(loggedInUserId, productId, quantity);
		return ResponseEntity.ok("Cart Item Quantity Updated Successfully");
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam Integer productId, HttpSession session) {
	    Integer userId = (Integer) session.getAttribute("loggedInUser");
	    cartItemService.removeItem(userId, productId);
	    return ResponseEntity.ok("Item removed from cart");
	}
	
}
