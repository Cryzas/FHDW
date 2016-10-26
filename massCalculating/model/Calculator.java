package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public abstract class Calculator {

	protected String description;
	private final Container<Integer> buffer1;
	private final Container<Integer> buffer2;
	private final Buffer<Integer> outputBuffer;

	protected Calculator(Container<Integer> buffer1, Container<Integer> buffer2,
			Buffer<Integer> outputBuffer) {
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		this.outputBuffer = outputBuffer;
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						Calculator.this.outputBuffer
								.put(compute(Calculator.this.buffer1.get(), Calculator.this.buffer2.get()));
					} catch (StoppException e) {
						running = false;
						outputBuffer.stopp();
					}
				}
			}
		}, description).start();
	}

	public abstract int compute(int int1, int int2);
}
