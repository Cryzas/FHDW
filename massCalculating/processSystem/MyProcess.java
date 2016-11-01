package processSystem;

import lockAndBuffer.Container;

public interface MyProcess<E> {

	Container<E> getOutputBuffer();
	
}
