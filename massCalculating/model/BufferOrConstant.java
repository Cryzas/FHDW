package model;

import lockAndBuffer.Buffer.StoppException;

public interface BufferOrConstant<E> {

	public E get() throws StoppException;

}
