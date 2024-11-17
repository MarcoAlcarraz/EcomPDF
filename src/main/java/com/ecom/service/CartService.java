package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import com.ecom.model.Cart;
import com.ecom.repository.CartRepository; // Se asume que tienes este repositorio

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;

@Service
public interface CartService {

	public Cart saveCart(Integer productId, Integer userId);

	public List<Cart> getCartsByUser(Integer userId);
	
	public Integer getCountCart(Integer userId);

	public void updateQuantity(String sy, Integer cid);



	
	
	

}
