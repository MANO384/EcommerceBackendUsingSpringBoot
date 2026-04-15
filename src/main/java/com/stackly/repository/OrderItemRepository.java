package com.stackly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stackly.entity.OrderItem;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Integer>{

}
