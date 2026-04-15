package com.stackly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackly.dto.CartResponse;
import com.stackly.entity.CartItem;
import com.stackly.entity.Product;
import com.stackly.entity.User;
import com.stackly.repository.CartItemRepository;

@Service
public class CartItemService {
    
	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	
	
	//ADD ITEMS TO CART
	public void addProductToCart(Integer userId, Integer productId, Integer quantity) {
	   
		Optional<CartItem> existingCartItem = cartItemRepo.findByUserIdAndProductProductId(userId,productId);
		
		if(existingCartItem.isPresent()) {	
			CartItem item = existingCartItem.get();
			item.setQuantity(item.getQuantity()+quantity);
			cartItemRepo.save(item);
		}
		
		else {
			CartItem newItem = new CartItem();	
			User user = userService.getUser(userId);
			Product product = productService.getProductById(productId);
			
			newItem.setUser(user);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			
			cartItemRepo.save(newItem);
		}	
		
	}
	
	
	//GET THE CART ITEMS
	public CartResponse getCartDetails(Integer userId) {
	   List<CartItem> cartItem = cartItemRepo.findByUserId(userId);
	   
	   double total = 0 ;
	   for(CartItem cI : cartItem) {
		  double price = cI.getProduct().getPrice();
		  int quantity = cI.getQuantity();
		  double totalPrice = price*quantity;
		  
		  total += totalPrice;
	   }
	   
	   CartResponse cartResponse  = new CartResponse();
	   cartResponse.setItems(cartItem);
	   cartResponse.setGrandTotal(total);
	   
	   return cartResponse;
	}
	
	
	//UPDATE THE CART
	public void updateQuantity(Integer userId, Integer productId, Integer newQty) {
	    Optional<CartItem> itemOpt = cartItemRepo.findByUserIdAndProductProductId(userId, productId);
	    
	    if (itemOpt.isPresent()) {
	        CartItem item = itemOpt.get();
	        item.setQuantity(newQty);
	        cartItemRepo.save(item);
	    }
	}
	
	//DELETE ITEM IN CART
	public void removeItem(Integer userId, Integer productId) {
	    Optional<CartItem> itemOpt = cartItemRepo.findByUserIdAndProductProductId(userId, productId);
	    if (itemOpt.isPresent()) {
	        cartItemRepo.delete(itemOpt.get());
	    }
	}

}
