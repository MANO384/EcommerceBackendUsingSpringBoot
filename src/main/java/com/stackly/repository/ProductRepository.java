package com.stackly.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackly.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
 
	List<Product> findByNameContainingIgnoreCase(String name);

	Page<Product> findAll(Pageable pageable);
}
