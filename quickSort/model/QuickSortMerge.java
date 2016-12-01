package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class QuickSortMerge<T extends Comparable<T>> {

	private final Buffer<T> inputBuffer;
	private final Buffer<T> inputBuffer2;
	private final T representant;
	private Buffer<T> outputBuffer;

	public QuickSortMerge(Buffer<T> inputBuffer, T representant, Buffer<T> inputBuffer2, Buffer<T> outputBuffer) {
		this.inputBuffer = inputBuffer;
		this.inputBuffer2 = inputBuffer2;
		this.representant = representant;
		this.outputBuffer = outputBuffer;
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				boolean running2 = true;
				while (running) {
					try {
						outputBuffer.put(inputBuffer.get());
					} catch (StoppException e) {
						running = false;
					}
				}
				outputBuffer.put(representant);
				while (running2) {
					try {
						outputBuffer.put(inputBuffer2.get());
					} catch (StoppException e) {
						running2 = false;
						outputBuffer.stopp();
					}
				}

			}
		}, "QuickSortMerge").start();
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}
}
