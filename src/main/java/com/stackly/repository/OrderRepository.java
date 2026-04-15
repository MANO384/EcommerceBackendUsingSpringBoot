package com.stackly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stackly.entity.Order;
import com.stackly.entity.User;

public interface OrderRepository extends CrudRepository<Order,Integer>{

	List<Order> findByUser(User user);

}
