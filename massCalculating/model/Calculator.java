package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;
import processSystem.MyProcess;

public abstract class Calculator implements MyProcess<Integer> {

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

	public Container<Integer> getFirstBuffer() {
		return this.buffer1;
	}

	public Container<Integer> getSecondBuffer() {
		return this.buffer2;
	}

	@Override
	public Buffer<Integer> getOutputBuffer() {
		return this.outputBuffer;
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
					} catch (ErrorInCalcException e) {
						running = false;
						outputBuffer.putCalcErrorException();
					} 
				}
			}
		}, description).start();
	}

	public abstract int compute(int int1, int int2) throws ErrorInCalcException;
}
