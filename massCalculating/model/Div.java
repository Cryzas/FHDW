package model;

import lockAndBuffer.Buffer;

public class Div extends Calculator {

	public static Div create(Container<Integer> dividendBuffer, Container<Integer> divisorBuffer,
			Buffer<Integer> outputBuffer) {
		return new Div(dividendBuffer, divisorBuffer, outputBuffer);
	}

	private Div(Container<Integer> buffer1, Container<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Div";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 / int2;
	}

}
