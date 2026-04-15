package com.stackly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackly.entity.User;
import com.stackly.service.ProductService;
import com.stackly.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
	private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(userService.getUser(id));
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		userService.addUser(user);
		return ResponseEntity.ok("User registered Successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session) {
		Boolean isValid = userService.loginVerification(user);
		if(isValid) {
			Integer id3 = userService.findByEmailToGetId(user.getEmail());
			session.setAttribute("loggedInUser", id3);
			return ResponseEntity.ok(productService.getAllProducts(0, 10, null, null));
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Integer id,@RequestBody User user) {
	     return	ResponseEntity.ok(userService.updateUser(id, user));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("user deleted successfully");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("logged out successfully");
	}
}
