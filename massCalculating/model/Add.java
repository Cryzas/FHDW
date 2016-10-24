package model;

import lockAndBuffer.Buffer;

public class Add extends Calculator{
	
	public static Add create(Buffer<Integer> summand1Buffer, Buffer<Integer> summand2Buffer,
			Buffer<Integer> outputBuffer) {
		return new Add(summand1Buffer, summand2Buffer, outputBuffer);
	}

	private Add(Buffer<Integer> buffer1, Buffer<Integer> buffer2, Buffer<Integer> outputBuffer) {
		super(buffer1, buffer2, outputBuffer);
		this.description = "Add";
	}

	@Override
	public int compute(int int1, int int2) {
		return int1 + int2;
	}
	
}
