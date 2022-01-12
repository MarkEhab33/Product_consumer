package com.example.Product_Consumer;



import com.example.Product_Consumer.dto.WSService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Converter.ParseData;
import SnapShot.Container;
import SnapShot.State;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/productconsumer")
public class ProductConsumerApplication {
	private Driver d;
	private ParseData p = new ParseData();
	FrontService frontService;
	public static void main(String[] args) {
		SpringApplication.run(ProductConsumerApplication.class, args);
	}

	@Autowired
	public ProductConsumerApplication(FrontService service){
		this.frontService=service;
		this.d=new Driver(this.frontService);
	}

	@PostMapping("/data")				// request for get the data 
	public String getData(@RequestBody String data) throws InterruptedException {


		d.setQueuesIDs(p.GetList(data, "queues"));
		d.setMachinesIDs(p.GetList(data, "machines"));
		d.setConnectionMap(p.GetMap(data, "fromTo"));
		d.CreateQueues();
		d.CreateMachines();
		d.Connections();
		return "Done with connections";
	}
	
	@PostMapping("/start")
	public void startSimulation(@RequestBody String numberOfProducts) throws InterruptedException{
		int products = Integer.parseInt(numberOfProducts);
		Machine.exit = false;
		d.CreateProducts(products);
		Thread.sleep(3000);
		d.StartSimulation();
	}
	
	@PostMapping("/replay")
	public void Replay() {
		System.out.println(".... REPLAY");
		d.replay();
	
	}
	@PostMapping("/end")
	public void endSimulation(){
		Machine.exit = true;
		System.out.println(Machine.exit);
	}
		
}
