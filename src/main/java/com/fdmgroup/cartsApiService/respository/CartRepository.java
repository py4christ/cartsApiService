package com.fdmgroup.cartsApiService.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.cartsApiService.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
