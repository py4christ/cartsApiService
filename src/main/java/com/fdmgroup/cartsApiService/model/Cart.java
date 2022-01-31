package com.fdmgroup.cartsApiService.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="carts")
public class Cart {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToMany
	private List<Product> items;
	
	private double totalPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Product> getItems() {
		return items;
	}
	public void setItems(List<Product> items) {
		this.items = items;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + ", totalPrice=" + totalPrice + "]";
	}
	
	
}
