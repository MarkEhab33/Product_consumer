package com.example.Product_Consumer;

import java.util.Random;

public class Product {
	
	private String [] colors = {"green","red","blue","yellow","purple","brown","coral","fuchsia"};
	private String colour;
	public Product() {

		this.colour=colors[Driver.colorIndex%8];
		Driver.colorIndex++;
		System.out.println("A new product created with colour "+this.colour);
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
