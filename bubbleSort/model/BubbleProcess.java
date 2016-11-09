package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public class BubbleProcess {

	private Buffer<Integer> inputBuffer;
	private Buffer<Integer> outputBuffer;
	boolean callSupport = false;

	public BubbleProcess(Buffer<Integer> inputBuffer) {
		this.inputBuffer = inputBuffer;
		this.outputBuffer = new Buffer<Integer>(100);
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Integer first = null;
				Integer second = null;
				boolean running = true;
				try {
					first = inputBuffer.get();
				} catch (StoppException | ErrorInCalcException e1) {
					running = false;
					outputBuffer.stopp();
				}
				try {
					second = inputBuffer.get();
				} catch (StoppException | ErrorInCalcException e1) {
					running = false;
					outputBuffer.put(first);
					outputBuffer.stopp();
				}
				while (running) {
					try {
						if (first <= second) {
							outputBuffer.put(first);
							first = second;
							second = inputBuffer.get();
						} else {
							outputBuffer.put(second);
							second = inputBuffer.get();
							BubbleProcess.this.callSupport = true;
						}
					} catch (StoppException e) {
						outputBuffer.put(first);
						outputBuffer.stopp();
						running = false;
					} catch (ErrorInCalcException e) {
						running = false;
						outputBuffer.putCalcErrorException();
					}
				}
			}
		}, "BubbleProcess").start();
	}

	public Buffer<Integer> getOutputBuffer() {
		return this.outputBuffer;
	}

	public boolean needsMedic() {
		return callSupport;
	}

}
