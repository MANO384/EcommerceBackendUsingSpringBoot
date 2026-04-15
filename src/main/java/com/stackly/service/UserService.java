package com.stackly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stackly.entity.Order;
import com.stackly.entity.User;
import com.stackly.repository.AddressRepository;
import com.stackly.repository.OrderRepository;
import com.stackly.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepo;
  @Autowired
  private AddressRepository addressRepo;
  @Autowired
  private OrderRepository orderRepo;
  
  //ADDUSER
  public User addUser(User user) {
	  if(user.getName() == null || user.getEmail()==null || user.getPassword()==null) {
		  throw new RuntimeException("Name ,email and password are required");
	  }
	  return userRepo.save(user);
  }
  
  //GETALLUSERS
  public List<User> getAllUser(){
	 return  (List<User>)userRepo.findAll();
  }
  
  //GETUSERBYID
  public User getUser(int id) {
	  return userRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("User with id : "+id+" not found"));
  }
  
  //DELETEUSER
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteUser(int id) {
	  User user = userRepo.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
	   
	  List<Order> orders = orderRepo.findByUser(user);
	    
	    for (Order order : orders) {
	        order.setUser(null); 
	    }
	    orderRepo.saveAll(orders);
	  
	  if(user.getAddress() != null) {
	    	addressRepo.delete(user.getAddress());
	    }
	    userRepo.delete(user);
	}
  
  public User updateUser(int id, User user) {
	    User existingUser = userRepo.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
	    if(user.getName() != null) {
	        existingUser.setName(user.getName());
	    }
	    if(user.getEmail() != null) {
	        existingUser.setEmail(user.getEmail());
	    }
	    return userRepo.save(existingUser);
	}

  public boolean loginVerification(User user) {
	 
	  Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
	  
	  if(existingUser.isPresent()) {
		 User us1 =  existingUser.get();
		 return us1.getPassword().equals(user.getPassword());
	  }
	  return false;
  }

  public Integer findByEmailToGetId(String email) {
	  return userRepo.findByEmail(email).get().getId(); 
  }
  
}
