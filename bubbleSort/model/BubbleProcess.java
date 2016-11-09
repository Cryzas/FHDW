package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class BubbleProcess<T extends Comparable<T>> {

	private Buffer<T> inputBuffer;
	private Buffer<T> outputBuffer;
	boolean startedNext = false;
	private BubbleManager<T> manager;

	public BubbleProcess(Buffer<T> inputBuffer, BubbleManager<T> manager) {
		this.inputBuffer = inputBuffer;
		this.outputBuffer = new Buffer<T>(10000000);
		this.manager = manager;
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				T first = null;
				T second = null;
				boolean running = true;
				try {
					first = inputBuffer.get();
				} catch (StoppException e) {
					running = false;
					outputBuffer.stopp();
				}
				if (running) {
					try {
						second = inputBuffer.get();
					} catch (StoppException e) {
						running = false;
						outputBuffer.put(first);
						outputBuffer.stopp();
					}
				}

				while (running) {
					try {
						if (first.compareTo(second)<=0) {
							outputBuffer.put(first);
							first = second;
							second = inputBuffer.get();
						} else {
							// swap Items
							outputBuffer.put(second);
							second = inputBuffer.get();
							if (!startedNext) {
								manager.startNew(outputBuffer);
								startedNext = true;
							}
						}
					} catch (StoppException e) {
						outputBuffer.put(first);
						outputBuffer.stopp();
						running = false;
					}
				}
				manager.logOut(BubbleProcess.this);
			}
		}, "BubbleProcess").start();
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}

}
