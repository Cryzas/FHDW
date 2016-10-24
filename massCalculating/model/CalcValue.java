package model;

import lockAndBuffer.Buffer.StoppException;

public interface CalcValue<E> {
	
	public E get() throws StoppException;

}
