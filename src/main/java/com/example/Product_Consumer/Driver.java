package com.example.Product_Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	public static int NumberOfProducts=0;
	public static queue EndQueue;
	private FrontService frontService;
	public static int colorIndex=0;
	
	@Autowired
	public Driver(FrontService service){
		this.frontService=service;
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

					try {
						ObjectMapper mapper = new ObjectMapper();

						String jsonString = mapper.writeValueAsString(c.getSteps().pop().getUpdate());
						System.out.println(c.getSteps().size()+" updates left to be sent ");
						System.out.println(jsonString);
						this.frontService.sendToFront(jsonString);

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
//					System.out.println(c.getSteps().pop().toString());
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
		this.NumberOfProducts=number;
		this.EndQueue=this.findThequeueByID("Qx");
		for(int i=0 ; i<number ;i++) {
			Product p = new Product();
			this.AllQueues.get(0).addToMyProducts(p);
		}
	}
	
	public void StartSimulation() {
		this.startTime=System.currentTimeMillis();
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
				System.out.println(from + " >>>> " + to);
				que.addTotoMachine(mach);
				mach.addToFromQueue(que);
			}else {
				System.out.println("from is " + from) ;
				mach = findTheMachineByID(from);
				que = findThequeueByID(to);
				System.out.println(from + " >>>> " + to);
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
	
	
	public void setQueuesIDs(List<String> queuesIDs) {
		this.queuesIDs = queuesIDs;
	}

	public void setMachinesIDs(List<String> machinesIDs) {
		this.machinesIDs = machinesIDs;
	}


	public void setConnectionMap(HashMap<String, List<String>> connectionMap) {
		this.connectionMap = connectionMap;
	}
	
}
