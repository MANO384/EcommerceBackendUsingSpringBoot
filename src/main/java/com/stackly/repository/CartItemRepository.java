package com.stackly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stackly.entity.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem,Integer> {

	Optional<CartItem> findByUserIdAndProductProductId(Integer userId, Integer productId);

	List<CartItem> findByUserId(Integer userId);
    
	@Modifying
	@Transactional
	void deleteByUserId(Integer userId);

}
