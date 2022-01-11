package SnapShot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Container {
	
	private Deque <State> steps ;
	
	public Container() {
		steps=new ArrayDeque<State>();
	}
	
	public void AddToMySteps(State s) {
		System.out.println("the state added is ");
		System.out.println(s.toString());
		steps.add((State) s.clone());
	}

	public Deque<State> getSteps() {
		return steps;
	}

}
