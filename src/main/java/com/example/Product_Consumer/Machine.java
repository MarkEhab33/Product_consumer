package com.example.Product_Consumer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.Random;

import Observer.Observable;
import SnapShot.Container;
import SnapShot.State;
import SnapShot.Update;
import com.example.Product_Consumer.dto.WSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

public class Machine implements Runnable,Observable {
	private int[] times= {1000,2000,3000,4000,5000,6000,7000,8000,9000};
	private int perioud ;
	private String id;
	private boolean avaliable;
	private ArrayList<queue> fromQueue;
	private queue toQueue;
	public Product currentProduct;
	SimpMessagingTemplate simpMessagingTemplate;
	FrontService frontService;

	@Autowired
	public Machine(FrontService service) {
		Random r = new Random();
		int ind =r.nextInt(times.length);
		this.perioud= times[ind];
		this.avaliable = true;
		this.fromQueue=new ArrayList<queue>();
		this.toQueue = new queue();
		this.frontService=service;
	}
	
	public void launch() {
		
		Thread t = new Thread(this);
		t.start();
	}


	@Override
	public void run() {
		
		
		Update update = new Update();
		State state = new State();
			try {
				this.fromQueue=this.getFromQueue();
				System.out.println("Enter the Run by machine "+this.id + " with time sleep "+ this.perioud);
				while(Driver.EndQueue.getProducts().size()<Driver.NumberOfProducts) {
				for(int i=0 ; i<this.fromQueue.size();i++) {

					while(!this.fromQueue.get(i).getProducts().isEmpty()) {
						
						this.currentProduct= this.fromQueue.get(i).getProducts().poll();
						System.out.println("Current Serve product is "+this.currentProduct.getColour()+" in machine "+this.id);
						update.setMachineColour(this.currentProduct.getColour());
						update.setMachineID(this.getId());
						update.setQueueID(this.fromQueue.get(i).getId());
						update.setQueueNum(Integer.toString(this.fromQueue.get(i).getProducts().size()));
						ObjectMapper mapper = new ObjectMapper();
						state=new State(update,Driver.startTime);
						String jsonString = mapper.writeValueAsString(update);
						this.frontService.sendToFront(jsonString);
						System.out.println("eh el 7alawa di "+jsonString);
						Driver.c.AddToMySteps(state);
				
						Thread.sleep(perioud);
						this.toQueue.addToMyProducts(currentProduct);
						//System.out.println("After add item to toQueue the size becomes"+ this.toQueue.getProducts().size());
						update.setMachineColour("white");
						update.setMachineID(this.getId());
						update.setQueueID(this.toQueue.getId());
						update.setQueueNum(Integer.toString(this.toQueue.getProducts().size()));

						jsonString = mapper.writeValueAsString(update);
						this.frontService.sendToFront(jsonString);
						System.out.println("eh el 7alawa di "+jsonString);
						state=new State(update,Driver.startTime);
						
						Driver.c.AddToMySteps(state);

						}
					}
				}
			
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}}
		
//			notifyAllSubscribers();
//			try {
//				System.out.println("Start work with product "+this.currentProduct.getColour()+" "+ this.perioud);
//				Thread.sleep(perioud);
//				System.out.println("End work with product");
//			}catch(Exception e){
//				e.printStackTrace();
//			}	
//	}
//		try {
//			while(!this.fromQueue.isEmpty()) {
//				if(this.avaliable) {
//					notifyAllSubscribers();
//					System.out.println("time of the machine = "+perioud);
//					System.out.println("Current Serve product is "+this.currentProduct.getColour()+" in machine "+this.id);
//					update.setMachineColour(this.currentProduct.getColour());
//					update.setMachineID(this.getId());
//			//		update.setQueueID(this.fromQueue.get(i).getId());
//			//		update.setQueueNum(Integer.toString(this.fromQueue.get(i).getProducts().size()));
//
//					state=new State(update,Driver.startTime);
//					Driver.c.AddToMySteps(state);
//			
//					Thread.sleep(perioud);
//					this.avaliable=false;
//				}else{
//					System.out.println("offfff");
//				//	notifyAllSubscribers();
//					this.toQueue.addToMyProducts(currentProduct);
//					update.setMachineColour("white");
//					update.setMachineID(this.getId());
//					update.setQueueID(this.toQueue.getId());
//					update.setQueueNum(Integer.toString(this.toQueue.getProducts().size()));
//				
//					state=new State(update,Driver.startTime);
//					Driver.c.AddToMySteps(state);
//					
//					this.avaliable=true;
//				}
//			}
//			
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
	
	
	
	
	public void addToFromQueue(queue q) {
		System.out.println("An queue added to FromQueue "+q.getId()+" to the machine "+ this.id);
		this.fromQueue.add(q);
	   
	}
	
	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<queue> getFromQueue() {
		return fromQueue;
	}

	public void setFromQueue(ArrayList<queue> fromQueue) {
		this.fromQueue = fromQueue;
	}

	public queue getToQueue() {
		return toQueue;
	}

	public void setToQueue(queue toQueue) {
		System.out.println("the toQueue for the machine of id "+this.id+" is queue with id "+toQueue.getId());
		this.toQueue = toQueue;
	}


	@Override
	public void notifyAllSubscribers() {
		System.out.println("Notify now");
		for(int i=0 ;i<this.fromQueue.size();i++){
			this.fromQueue.get(i).update(this);
		}	
	}
	
	public boolean getAvaliability() {
		return this.avaliable;	
	}
	
}