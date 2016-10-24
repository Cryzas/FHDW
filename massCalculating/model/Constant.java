package model;

public class Constant<E> implements CalcValue<E>{

	E value;
	
	public Constant(E value) {
		this.value = value;
	}
	
	public E get(){
		return value;		
	}

}
