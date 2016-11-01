package model;

import lockAndBuffer.Buffer;

public class Var implements MyProcess<Integer> {
	
	private Buffer<Integer> buffer;
	
	public Var(final Buffer<Integer> buffer) {
		this.buffer = buffer;
	}

	@Override
	public Buffer<Integer> getOutputBuffer() {
		return this.buffer;
	}

}
