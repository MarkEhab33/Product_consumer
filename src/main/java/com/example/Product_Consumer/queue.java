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
	
	

	public void addToMyProducts(Product p) {
		System.out.println("Element will added to the products  of queue with id "+this.id+" "+ p.getColour());
		this.products.push(p);;
	}
	
	public void addTotoMachine(Machine m) {
		System.out.println("the machine is added to TOMachine "+m.getId()+"to the queue "+this.id);
		this.toMachine.add(m);
	}
	

	public void addToFromMachine(Machine m) {
		System.out.println("the machine is added to FromMachine "+m.getId()+"to the queue "+this.id);
		this.fromMachine.add(m);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	synchronized public Deque<Product> getProducts() {
		return products;
	}

	public void setProducts(Deque<Product> products) {
		this.products = products;
	}

	public ArrayList<Machine> getFromMachine() {
		return fromMachine;
	}

	public void setFromMachine(ArrayList<Machine> fromMachine) {
		this.fromMachine = fromMachine;
	}

	public ArrayList<Machine> getToMachine() {
		return toMachine;
	}

	public void setToMachine(ArrayList<Machine> toMachine) {
		this.toMachine = toMachine;
	}



	@Override
	public void update(Machine m) {
		// TODO Auto-generated method stub
		boolean canServe=m.getAvaliability();
		if((!this.products.isEmpty()) && canServe){
			m.currentProduct=this.cloneProduct(this.products.pop());
		}	
	}

	public Product cloneProduct(Product p) {
		Product newProduct = new Product();
		newProduct.setColour(p.getColour());
		return newProduct;
	}

}
