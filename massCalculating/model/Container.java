package model;

import lockAndBuffer.Buffer.StoppException;

public interface Container<E> {

	public E get() throws StoppException;

}
