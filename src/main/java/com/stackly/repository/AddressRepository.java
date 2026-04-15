package com.stackly.repository;

import org.springframework.data.repository.CrudRepository;

import com.stackly.entity.Address;

public interface AddressRepository extends CrudRepository<Address,Integer>{

}
