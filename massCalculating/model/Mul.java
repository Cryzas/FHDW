package model;

import lockAndBuffer.Buffer;

public class Mul extends Calculator {

	public static Mul create(Buffer<Integer> summand1Buffer, Buffer<Integer> summand2Buffer,
			Buffer<Integer> outputBuffer) {
		return new Mul(summand1Buffer, summand2Buffer, outputBuffer);
	}

	private Mul(Buffer<Integer> buffer1, Buffer<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Mul";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 * int2;
	}

}
