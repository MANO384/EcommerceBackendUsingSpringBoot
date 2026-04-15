package com.stackly.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stackly.entity.Product;
import com.stackly.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product addProduct(Product product) {
        if(product.getName() == null) {
            throw new RuntimeException("Product name is required");
        }
        return productRepo.save(product);
    }
 
    
    //pagination
    public Page<Product> getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String direction) {
    	    Sort sort = direction.equalsIgnoreCase("desc")?
    	    		Sort.by(sortBy).descending() :
    	    	  Sort.by(sortBy).ascending();
      	Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        return productRepo.findAll(pageable);
    }

    public Product getProductById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public void deleteProduct(Integer id) {
        if(!productRepo.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    public Product updateProduct(Integer id, Product product) {

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if(product.getName() != null) {
            existingProduct.setName(product.getName());
        }

        if(product.getPrice() != 0) { 
            existingProduct.setPrice(product.getPrice());
        }

        return productRepo.save(existingProduct);
    }
    
    
    public List<Product> searchProducts(String query) {
        try {
            Integer id = Integer.parseInt(query);
            return List.of(productRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No product with ID: " + id)));
        } catch (NumberFormatException e) {
            return productRepo.findByNameContainingIgnoreCase(query);
        }
    }
}