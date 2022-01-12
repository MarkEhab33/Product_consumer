package com.example.Product_Consumer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import java.util.Queue;

import Observer.Observer;

public class queue implements Observer {
	private String id;
	private Deque<Product> products;
	private ArrayList<Machine> fromMachine;
	private ArrayList<Machine> toMachine;
	private Machine machine;
	
	public queue() {
	
		products=new ArrayDeque<Product>();
		fromMachine=new ArrayList<Machine>();
		toMachine=new ArrayList<Machine>();
	}
	
	synchronized public void addToMyProducts(Product p) {
		
		this.products.push(p);;
	}
	
	synchronized public void addTotoMachine(Machine m) {
		
		this.toMachine.add(m);
	}
	
	synchronized public void addToFromMachine(Machine m) {
		
		this.fromMachine.add(m);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	 public Deque<Product> getProducts() {
		return products;
	}

	synchronized public void setProducts(Deque<Product> products) {
		this.products = products;
	}

	public ArrayList<Machine> getFromMachine() {
		return fromMachine;
	}

	public void setFromMachine(ArrayList<Machine> fromMachine) {
		this.fromMachine = fromMachine;
	}

	public Product cloneProduct(Product p) {
		Product newProduct = new Product();
		newProduct.setColour(p.getColour());
		return newProduct;
	}
	
@Override
 	synchronized public queue update(Machine m) {
	if(!this.products.isEmpty()){
		return this;
	}
	return null;
}

	synchronized public Product getOneProduct() {
	//kant get products 
		if(!this.products.isEmpty()) {
			return this.getProducts().pop();
		}
		return null;
		
	}
}
