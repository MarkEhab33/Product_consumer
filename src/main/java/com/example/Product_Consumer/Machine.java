package com.example.Product_Consumer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.Random;

import Observer.Observable;
import Observer.Observer;
import SnapShot.Container;
import SnapShot.State;
import SnapShot.Update;
import com.example.Product_Consumer.dto.WSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

public class Machine implements Runnable,Observable {
	private int[] times= {2000,3000,4000,5000};
	private int perioud ;
	private String id;
	public static boolean exit;
	private ArrayList<queue> fromQueue;
	private queue toQueue;
	public Product currentProduct;
	private queue serve;
	Thread t = new Thread(this);
	SimpMessagingTemplate simpMessagingTemplate;
	FrontService frontService;

	@Autowired
	public Machine(FrontService service) {
		Random r = new Random();
		int ind =r.nextInt(times.length);
		this.perioud= times[ind];
		this.fromQueue=new ArrayList<queue>();
		this.toQueue = new queue();
		this.frontService=service;
	}

	public void launch() {
		
		t.start();
	}

	public void stop() throws InterruptedException {
		
		t.join();
	}
	
	
	@Override
	public void run() {
		while (!exit) {
			Update update = new Update();
			State state = new State();

			try {
				while ((Driver.EndQueue.getProducts().size() < Driver.NumberOfProducts) && (!exit)) {
					this.notifyAllSubscribers();
					if (this.serve != null) {
						this.currentProduct = this.serve.getOneProduct();
						if (this.currentProduct != null) {
							update.setMachineColour(this.currentProduct.getColour());
							update.setMachineID(this.getId());
							update.setQueueID(this.serve.getId());
							update.setQueueNum(Integer.toString(this.serve.getProducts().size()));
							ObjectMapper mapper = new ObjectMapper();
							String jsonString = mapper.writeValueAsString(update);
							this.frontService.sendToFront(jsonString);
							System.out.println(jsonString);
							state = new State(update, Driver.startTime);
							Driver.c.AddToMySteps(state);
							Thread.sleep(perioud);
							this.toQueue.addToMyProducts(currentProduct);
							System.out.println(this.toQueue.getProducts().size());
							update.setMachineColour("white");
							update.setMachineID(this.getId());
							update.setQueueID(this.toQueue.getId());
							update.setQueueNum(Integer.toString(this.toQueue.getProducts().size()));

							jsonString = mapper.writeValueAsString(update);
							this.frontService.sendToFront(jsonString);
							System.out.println(jsonString);

							state = new State(update, Driver.startTime);
							Driver.c.AddToMySteps(state);
						}
					}
				}


				System.out.println("Machine " + this.getId() + " end ");
				Driver.endMachineCount();

			} catch (Exception e) {
				System.out.println("From catch failed in machine >>>>> " + this.getId());
				e.printStackTrace();
			}
			if (Driver.EndQueue.getProducts().size() == Driver.NumberOfProducts){
				exit = true;
			}
			}
//		}
//			if (Driver.EndQueue.getProducts().size() >= Driver.NumberOfProducts) {
//				try {
//					this.frontService.sendToFront("disconnect");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println("disc");
//			}

			System.out.println("Thread of Machine " + this.getId() + " ENDED");

	}
		
	synchronized public void addToFromQueue(queue q) {
		this.fromQueue.add(q);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public queue getToQueue() {
		return toQueue;
	}

	public void setToQueue(queue toQueue) {
		this.toQueue = toQueue;
	}

	@Override
	public void notifyAllSubscribers() {
		for(int i=0 ;(i<this.fromQueue.size());i++){
			if(this.serve==null || this.serve.isEmpty()) {
				this.serve =  (this.fromQueue).get(i).update(this);
			}
		}
	}
	
}
