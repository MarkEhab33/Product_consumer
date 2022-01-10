package com.example.Product_Consumer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import java.util.Queue;

public class queue implements Observer {
	private String id;
	private Deque<Product> products;
	private ArrayList<Machine> fromMachine;
	private ArrayList<Machine> toMachine;

	
	public queue() {
	
		products=new ArrayDeque<Product>();
		fromMachine=new ArrayList<Machine>();
		toMachine=new ArrayList<Machine>();
	}

	public void addToMyProducts(Product p) {
		System.out.println("Element will added to the products  of queue with id "+this.id+ p.getColour());
		this.products.add(p);
		System.out.println("My last product is :"+this.products.peekLast().getColour());
	}
	
	public void addTotoMachine(Machine m) {
		System.out.println("the machine is added to TOMachine"+m.getId());
		this.toMachine.add(m);
		
	}
	

	public void addToFromMachine(Machine m) {
		System.out.println("the machine is added to FromMachine"+m.getId());
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
	public void update(boolean avaliable, String id) {
		// TODO Auto-generated method stub
		System.out.println("Update function ");
		System.out.println("Search for id " + id);
			for(int i=0 ; i<this.toMachine.size();i++) {
				Machine m = this.toMachine.get(i);
				String ID = m.getId();
				System.out.println("id founded "+ID);
				if(ID.equals(id)) {
					System.out.println(m.getId());
					System.out.println("Will serve now :"+this.products.poll().getColour());
					m.currentProduct=this.products.poll();
					System.out.println("My products now"+this.products);
					m.run();
					break;
				
			}
		}
		
	}
}
