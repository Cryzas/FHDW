package model;

import lockAndBuffer.Container;

public interface MyProcess<E> {

	Container<E> getOutputBuffer();
	
}
