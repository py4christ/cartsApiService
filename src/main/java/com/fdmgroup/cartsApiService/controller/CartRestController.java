package com.fdmgroup.cartsApiService.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.cartsApiService.model.Cart;
import com.fdmgroup.cartsApiService.model.Product;
import com.fdmgroup.cartsApiService.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
public class CartRestController {
	
	private CartService cartService;

	@Autowired
	public CartRestController(CartService cartService) {
		super();
		this.cartService = cartService;
	}
	

	@GetMapping()
	public ResponseEntity<List<Cart>> getCarts() {
		return ResponseEntity.ok(cartService.readCarts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cart> getCart(@PathVariable int id) {
		return ResponseEntity.ok(cartService.readCartById(id));
	}
	
	@PostMapping
	public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
		Cart createdCart = cartService.createCart(cart);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cart.getId())
				.toUri();
	//	return ResponseEntity.created(location).build();
		return ResponseEntity.created(location).body(createdCart);
	}
	
	@PutMapping()
	public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
		cartService.readCartById(cart.getId());
		cartService.updateCart(cart);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cart.getId}")
				.buildAndExpand(cart.getId()).toUri();
		return ResponseEntity.created(location).body(cart);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
		cartService.readCartById(id);
		cartService.deleteCart(id);
	}
	
	@GetMapping("/available_products")
	public ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.ok(cartService.readAvailableProducts());
	}
	
	
	@PutMapping({"/addItem/{productId}", "/addItem/{productId}/{cartId}"})
	public void addItemToCart(@Autowired Cart cart, @PathVariable int productId, @PathVariable(required = false) Integer cartId) {
		
		if (cartId == null) {
			cart = cartService.createCart(cart);
		}else {
			cart = cartService.readCartById(cartId);
		}
		cartService.addItemToCart(cart, productId);	
	}
	
	
	@PutMapping("/removeItem/{productId}/{cartId}")
	public void removeItemFromCart(@PathVariable int productId, @PathVariable int cartId) {
		Cart cart = cartService.readCartById(cartId);
		cartService.removeItemFromCart(cart, productId);
	}
	
	@PutMapping("/clearAllItems/{cartId}")
	public void clearAllItemsFromCart(@PathVariable int cartId) {
		Cart cart = cartService.readCartById(cartId);
		cartService.clearAllItemsFromCart(cart);
	}
	
	

}
