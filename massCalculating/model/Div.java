package model;

import lockAndBuffer.Buffer;

public class Div extends Calculator {

	public static Div create(CalcValue<Integer> dividendBuffer, CalcValue<Integer> divisorBuffer,
			Buffer<Integer> outputBuffer) {
		return new Div(dividendBuffer, divisorBuffer, outputBuffer);
	}

	private Div(CalcValue<Integer> buffer1, CalcValue<Integer> buffer2, Buffer<Integer> outputBuffer) {
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
