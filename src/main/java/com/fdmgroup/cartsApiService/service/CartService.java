package com.fdmgroup.cartsApiService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.cartsApiService.exception.CartNotFoundException;
import com.fdmgroup.cartsApiService.model.Cart;
import com.fdmgroup.cartsApiService.model.Product;
import com.fdmgroup.cartsApiService.respository.CartRepository;

@Service
public class CartService {
	
	private CartRepository cartRepo;
	
	@Autowired
	private ProductApiClient productApiClient;

	@Autowired
	public CartService(CartRepository cartRepo) {
		super();
		this.cartRepo = cartRepo;
	}
	
	public List<Cart> readCarts() {
		return cartRepo.findAll();
	}
	
	public Cart readCartById(int id) {
		 Optional<Cart> optCart = cartRepo.findById(id);
		 if (!optCart.isPresent()) {
			 throw new CartNotFoundException("Cart not found for id " + id);
		 }
		 return optCart.get();
	}
	
	public Cart createCart(Cart cart) {
		return cartRepo.save(cart);
	}
	
	public void updateCart(Cart cart) {
		cartRepo.save(cart);
	}
	
	public void deleteCart(int id) {
		cartRepo.deleteById(id);
	}
	
	public List<Product> readAvailableProducts() {
		return productApiClient.getProducts();
	}
	
	public void addItemToCart(Cart cart, int productId) {
		List<Product> items;
		Double totalPrice = 0.0;
	
		Product product = productApiClient.getProduct(productId);
		
		if (cart.getItems() == null) {
			items = new ArrayList<Product>();
		}else {
			items = cart.getItems();
		}
		items.add(product);
		
		for (Product item: items) {
			totalPrice = totalPrice + item.getPrice();
		}
		
		cart.setItems(items);
		cart.setTotalPrice(totalPrice);
		
		updateCart(cart);
	}

	
	public void removeItemFromCart(Cart cart, int productId) {
		Double totalPrice = 0.0;
		Product product = productApiClient.getProduct(productId);
		List<Product> items = cart.getItems();
		
		if (items != null && items.size() > 0) {
			items.remove(product);
			
			for (Product item: items) {
				totalPrice = totalPrice + item.getPrice();
			}
		}else {
			if (items.size() == 1) {
				items.clear();
				cart.setTotalPrice(0);
			}
		}
		
		cart.setItems(items);
		cart.setTotalPrice(totalPrice);
		
		updateCart(cart);	
	}
	
	public void clearAllItemsFromCart(Cart cart) {
		List<Product> tempItems = cart.getItems();
		tempItems.clear();
		cart.setItems(tempItems);
		cart.setTotalPrice(0);
		updateCart(cart);	
	}
	

}
