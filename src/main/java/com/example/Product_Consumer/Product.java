package com.example.Product_Consumer;

import java.util.Random;

public class Product {
	
	private String [] colors = {"green","red","blue","yellow","purple","brown","fuchsia","goldenrod","lime","olive"};
	private String colour;
	
	public Product() {
		Random r = new Random();
		int index = r.nextInt(colors.length);
		this.colour=colors[index];
		System.out.println("A new product created with colour "+this.colour);
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
