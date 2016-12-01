package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class State {
	
	private Map<Character, List<Pair<State, Character>>> stateSet;
	private String name;
	
	public static State create(String name) {
		return new State(name);
	}
	
	private State(String name) {
		this.name = name;
		this.stateSet = new HashMap<Character, List<Pair<State, Character>>>();
	}
	
	public void add(char input, char output, State s) { 
		List<Pair<State, Character>> statesWithOutput;
		if(stateSet.containsKey(input)) {
			statesWithOutput = stateSet.get(input);	
		} else {
			statesWithOutput = new LinkedList<Pair<State, Character>>();
		}
		statesWithOutput.add(new Pair<State, Character>(s, output));
		this.stateSet.put(input, statesWithOutput);
	}
	
	public List<Pair<State, Character>> get(char c) {
		if(stateSet.containsKey(c)) {
			return this.stateSet.get(c);
		}
		return new LinkedList<Pair<State, Character>>(); 
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
