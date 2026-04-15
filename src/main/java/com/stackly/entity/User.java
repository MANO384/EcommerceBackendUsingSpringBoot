package com.stackly.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private String name;
	private String email;
	private String phNumber;
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn (name="address_id")
	@JsonManagedReference
	private Address address;
	
	public User(String name,String email,String phNumber,String password) {
		this.name=name;
		this.email=email;
		this.phNumber = phNumber;
		this.password=password;
	}
	
	public User(String email,String password) {
		this.email=email;
		this.password=password;
	}
    
}
