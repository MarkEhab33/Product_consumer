package com.example.Product_Consumer;

import java.util.Random;

public class Product {
	
	private String [] colors = {"green","red","blue","yellow","purple","brown","coral","fuchsia"};
	private String colour;
	static int index = 0 ;
	public Product() {

		if(index==colors.length-1){
			index=0;
		}
		this.colour=colors[index];
		index++;
		System.out.println("A new product created with colour "+this.colour);
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
