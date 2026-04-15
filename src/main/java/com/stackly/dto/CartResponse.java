package com.stackly.dto;

import java.util.List;

import com.stackly.entity.CartItem;

import lombok.Data;

@Data
public class CartResponse {
	private List<CartItem> items;
    private Double grandTotal;
}
