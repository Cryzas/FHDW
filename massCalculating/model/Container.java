package model;

import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public interface Container<E> {

	public E get() throws StoppException, ErrorInCalcException;

}
