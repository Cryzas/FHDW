package lockAndBuffer;

import lockAndBuffer.Container;

public class Constant<E> implements Container<E> {

	E value;

	public Constant(E value) {
		this.value = value;
	}

	public E get() {
		return value;
	}

}
