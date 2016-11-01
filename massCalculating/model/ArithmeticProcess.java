package model;

import java.util.ArrayList;
import java.util.HashMap;

import arithmetic.Variable;
import lockAndBuffer.Buffer;
import lockAndBuffer.Container;

public class ArithmeticProcess {
	
	private HashMap<Variable, Cloner<Integer>> varCloner = new HashMap<Variable, Cloner<Integer>>();
	private HashMap<Container<Integer>, Cloner<Integer>> cloner = new HashMap<Container<Integer>, Cloner<Integer>>();
	private ArrayList<Buffer<Integer>> inputs = new ArrayList<Buffer<Integer>>();
	private Container<Integer> output;
	
	public void setOutput(Container<Integer> output){
		this.output = output;
	}

	public Container<Integer> addExpression(Container<Integer> input){
		Cloner<Integer> copier;
		if (cloner.containsKey(input)) {
			copier = cloner.get(input);
		} else {
			copier = new Cloner<Integer>(input);
			copier.start();
			cloner.put(input, copier);		
		}
		return copier.addNewClone();
	}
	
	public Container<Integer> getOutput(){
		return output;
	}
	
	public ArrayList<Buffer<Integer>> getInputs(){
		return inputs;
	}
	
	public Buffer<Integer> addVariable(Variable var){
		Cloner<Integer> copier;
		if (varCloner.containsKey(var)) {
			copier = varCloner.get(var);
		} else {
			Buffer<Integer> buffer = new Buffer<Integer>(10);
			copier = new Cloner<Integer>(buffer);
			copier.start();
			varCloner.put(var, copier);
			inputs.add(buffer);
		}
		return copier.addNewClone();
	}
	
}
