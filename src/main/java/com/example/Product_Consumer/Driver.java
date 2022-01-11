package com.example.Product_Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import SnapShot.Container;
import org.springframework.beans.factory.annotation.Autowired;

public class Driver {
	
	private List<String> queuesIDs ;
	private List<String> machinesIDs ;
	private HashMap<String,List<String>> connectionMap=new HashMap<String,List<String>>();
	private ArrayList <queue> AllQueues = new ArrayList <queue>();
	private ArrayList <Machine> AllMachines = new ArrayList <Machine>();
	public static long startTime;
	public static Container c = new Container();
	private FrontService frontService;

	@Autowired
	public Driver(FrontService service){
		this.frontService=service;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public void replay() {
		long startTimeOfReplay = System.currentTimeMillis();
		long atTime;
		long go;
		boolean end = true;
		
		while(end) {	
			if(!c.getSteps().isEmpty()) {			
				atTime = (System.currentTimeMillis())-startTimeOfReplay;
				go = c.getSteps().peek().getTime();
				
				if(atTime>go) {
					atTime=0;
					System.out.println(c.getSteps().pop().toString());
				}
			}else {
				end=false;
				System.out.println("Finish replay");
			}
		}
	}
	public void CreateQueues() {
		for(int i=0;i<this.queuesIDs.size();i++) {
			queue q = new queue();
			q.setId(this.queuesIDs.get(i));
			this.AllQueues.add(q);
			System.out.println(q.getId());
		}
	}
	
	public void CreateMachines() {
		for(int i=0;i<this.machinesIDs.size();i++) {
			Machine m = new Machine(this.frontService);
			m.setId(this.machinesIDs.get(i));
			this.AllMachines.add(m);
			System.out.println(m.getId());
		}
	}
	
	public void CreateProducts(int number) {
		for(int i=0 ; i<number ;i++) {
			Product p = new Product();
			this.AllQueues.get(0).addToMyProducts(p);
		}
	}
	
	public void StartSimulation() {
		this.setStartTime(System.currentTimeMillis());
		for(int i=0;i<this.AllMachines.size();i++) {
			this.AllMachines.get(i).launch();
		}
	}
	
	
	public void Connections() {
		String from,to="";
		queue que = new queue();
		Machine mach = new Machine(this.frontService);
		
		for(String k: this.connectionMap.keySet()) {
			from=this.connectionMap.get(k).get(0);
			to=this.connectionMap.get(k).get(1);
			if(from.startsWith("Q")){
				que = findThequeueByID(from);
				mach = findTheMachineByID(to);
				
				que.addTotoMachine(mach);
				mach.addToFromQueue(que);
			}else {
				mach = findTheMachineByID(from);
				que = findThequeueByID(to);
				
				mach.setToQueue(que);
				que.addToFromMachine(mach);
			}
		}
			
	}
	
	public queue findThequeueByID(String id) {
		queue find = new queue() ;
		for(int i=0 ; i<this.AllQueues.size();i++) {
			if((AllQueues.get(i).getId()).equals(id)) {
				find = AllQueues.get(i);
			}
		}
		return find;
		
	}
	
	public Machine findTheMachineByID(String id) {
		Machine find = new Machine(frontService);
		for(int i=0 ; i<this.AllMachines.size();i++) {
			if((AllMachines.get(i).getId()).equals(id)) {
				find = AllMachines.get(i);
			}
		}
		return find;
		
	}
	
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
	
	

}
