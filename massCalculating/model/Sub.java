package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;

public class Sub extends Calculator {

	public static Sub create(Container<Integer> subtrahendBuffer, Container<Integer> minuendBuffer,
			Buffer<Integer> outputBuffer) {
		return new Sub(subtrahendBuffer, minuendBuffer, outputBuffer);
	}

	private Sub(Container<Integer> buffer1, Container<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Sub";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 - int2;
	}

}
