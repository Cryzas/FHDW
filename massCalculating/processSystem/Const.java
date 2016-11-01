package processSystem;

import lockAndBuffer.Container;

public class Const implements MyProcess<Integer> {
	
	private Container<Integer> container;
	
	public Const(final Container<Integer> container) {
		this.container = container;
	}

	@Override
	public Container<Integer> getOutputBuffer() {
		return this.container;
	}

}
