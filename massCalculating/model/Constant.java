package model;

import lockAndBuffer.Buffer;

public class Constant extends Buffer<Integer>{

	Integer value;
	
	public Constant(int value) {
		super(1);
		this.value = value;
	}
	
	public Integer get(){
		return value;		
	}

}
