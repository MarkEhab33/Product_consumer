package com.example.Product_Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Driver {
	
	private List<String> queuesIDs ;
	private List<String> machinesIDs ;
	private HashMap<String,List<String>> connectionMap=new HashMap<String,List<String>>();

	
	

	public List<String> getQueuesIDs() {
		return queuesIDs;
	}

	public void setQueuesIDs(List<String> queuesIDs) {
		this.queuesIDs = queuesIDs;
	}

	public List<String> getMachinesIDs() {
		return machinesIDs;
	}

	public void setMachinesIDs(List<String> machinesIDs) {
		this.machinesIDs = machinesIDs;
	}

	public HashMap<String, List<String>> getConnectionMap() {
		return connectionMap;
	}

	public void setConnectionMap(HashMap<String, List<String>> connectionMap) {
		this.connectionMap = connectionMap;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*
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
		*/
		
	}
	
	

}
