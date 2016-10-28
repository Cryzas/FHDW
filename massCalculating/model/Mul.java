package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;

public class Mul extends Calculator {

	public static Mul create(Container<Integer> factor1Buffer, Container<Integer> factor2Buffer,
			Buffer<Integer> outputBuffer) {
		return new Mul(factor1Buffer, factor2Buffer, outputBuffer);
	}

	private Mul(Container<Integer> buffer1, Container<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Mul";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 * int2;
	}

}
