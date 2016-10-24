package model;

import lockAndBuffer.Buffer;

public class Sub extends Calculator {

	public static Sub create(Buffer<Integer> summand1Buffer, Buffer<Integer> summand2Buffer,
			Buffer<Integer> outputBuffer) {
		return new Sub(summand1Buffer, summand2Buffer, outputBuffer);
	}

	private Sub(Buffer<Integer> buffer1, Buffer<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Sub";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 - int2;
	}

}
