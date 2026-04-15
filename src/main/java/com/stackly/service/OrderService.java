package com.stackly.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.stackly.dto.CartResponse;
import com.stackly.dto.OrderRequest;
import com.stackly.entity.Address;
import com.stackly.entity.CartItem;
import com.stackly.entity.Order;
import com.stackly.entity.OrderItem;
import com.stackly.repository.CartItemRepository;
import com.stackly.repository.OrderRepository;

import org.springframework.transaction.annotation.Transactional; // ADD THIS

@Service
public class OrderService {
    @Autowired
    private CartItemRepository cartItemRepo;
    
    @Autowired
    private CartItemService cartItemService;
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Order placeOrder(Integer userId,OrderRequest request) {
		List<CartItem> cartItem = cartItemRepo.findByUserId(userId);
		
		if(cartItem.isEmpty()) {
			throw new RuntimeException("Cart is Empty!");
		}
		
		Order order = new Order();
		order.setUser(userService.getUser(userId));
		order.setOrderDate(LocalDateTime.now());
		order.setDeliveryDate(LocalDate.now().plusDays(5));
		if(request != null && request.getPhone()!=null) {
			order.setShippingPhone(request.getPhone());
		}
		else {
			order.setShippingPhone(userService.getUser(userId).getPhNumber());
		}
		
		if(request != null && request.getAddress() != null) {
			order.setShippingaddress(request.getAddress());
		}
		else {
			Address regAddr = userService.getUser(userId).getAddress();
			if(regAddr == null) {
				throw new RuntimeException("No address found!");
			}
			
			String fullAdress  = String.format("%s, %s, %s",regAddr.getStreet(),regAddr.getCity(),regAddr.getPincode());
            order.setShippingaddress(fullAdress);			
		}
		CartResponse  response = cartItemService.getCartDetails(userId);
		order.setTotalAmount(response.getGrandTotal());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem cI : cartItem) {
			OrderItem oI = new OrderItem();
			
			oI.setOrder(order);
			oI.setProduct(cI.getProduct());
			oI.setQuantity(cI.getQuantity());
			oI.setPriceAtPurchase(cI.getProduct().getPrice());
			
			orderItems.add(oI);
		}
		
		order.setOrderItems(orderItems);
		
		Order savedOrder = orderRepo.save(order);
		
		cartItemRepo.deleteByUserId(userId);
		return savedOrder;
	}

}
