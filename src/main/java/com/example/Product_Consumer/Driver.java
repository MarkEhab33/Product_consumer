package com.example.Product_Consumer;
public class Driver {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		Machine m = new Machine ();
		m.setId("Machine1");
		
		Machine n = new Machine();
		n.setId("Machine2");
		
		Product p1 = new Product();
		Product p2 = new Product();
		Product p3 = new Product();
		
		queue Q1 = new queue();
		Q1.setId("Queue1");
		Q1.addToMyProducts(p1);
		Q1.addToMyProducts(p2);
		Q1.addToMyProducts(p3);
		
		queue Q2 = new queue();
		Q2.setId("Queue2");
		
		queue Q3 = new queue();
		Q3.setId("Queue3");
	
		Q1.addTotoMachine(m);
		
		m.addToFromQueue(Q1);
		m.setToQueue(Q2);
		
		n.addToFromQueue(Q2);
		n.setToQueue(Q3);
		
		n.launch();
		m.launch();
			
		
		
		
			/*Thread t1 = new Thread(m);
			Thread t2 = new Thread(n);
			t1.start();
			
			t1.join();
			t2.start();
			t2.join();
		
		*/
		
	}

}
