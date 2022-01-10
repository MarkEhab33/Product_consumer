package com.example.Product_Consumer;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/productconsumer")
public class ProductConsumerApplication {
	Driver d = new Driver ();
	ParseData p = new ParseData();
	
	public static void main(String[] args) {
		SpringApplication.run(ProductConsumerApplication.class, args);
	}
	
	@PostMapping("/data")				// request for get the data 
	public String getData(@RequestBody String data){
		d.setQueuesIDs(p.GetList(data, "queues"));
		d.setMachinesIDs(p.GetList(data, "machines"));
		d.setConnectionMap(p.GetMap(data, "fromTo"));
		d.CreateQueues();
		d.CreateMachines();
		d.Connections();
		return "Done with connections";
	}
	
	@PostMapping("/start")
	public void startSimulation(@RequestBody String numberOfProducts){
		int products = Integer.parseInt(numberOfProducts);
		d.CreateProducts(products);
		d.StartSimulation();
	}
	
}
