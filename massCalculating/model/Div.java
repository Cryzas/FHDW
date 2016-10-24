package model;

import lockAndBuffer.Buffer;

public class Div extends Calculator {

	public static Div create(Buffer<Integer> summand1Buffer, Buffer<Integer> summand2Buffer,
			Buffer<Integer> outputBuffer) {
		return new Div(summand1Buffer, summand2Buffer, outputBuffer);
	}

	private Div(Buffer<Integer> buffer1, Buffer<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Div";
	}

	@Override
	public int compute(int int1, int int2) {
		if (int2 == 0)
			throw new Error();
		return int1 / int2;
	}

}
