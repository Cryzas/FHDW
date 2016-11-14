package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class MergeSort<T extends Comparable<T>> {

	private Buffer<T> inputBuffer;
	private Buffer<T> outputBuffer;

	public MergeSort(Buffer<T> input) {
		this.inputBuffer = input;
		outputBuffer = new Buffer<T>();
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final MergeSort<T> sortLeft = new MergeSort<T>(new Buffer<>());
				final MergeSort<T> sortRight = new MergeSort<T>(new Buffer<>());
				boolean running = true;
				T t = null;
				try {
					t = inputBuffer.get();
				} catch (final StoppException e) {
					running = false;
					sortLeft.inputBuffer.stopp();
					sortRight.inputBuffer.stopp();
					outputBuffer.stopp();
				}
				if (running) {
					try {
						sortRight.inputBuffer.put(inputBuffer.get());
						sortLeft.inputBuffer.put(t);
					} catch (final StoppException e) {
						running = false;
						sortLeft.inputBuffer.stopp();
						sortRight.inputBuffer.stopp();
						outputBuffer.put(t);
						outputBuffer.stopp();
					}
				}
				if (running) {
					final Merger<T> merger = new Merger<T>(sortLeft.outputBuffer, sortRight.outputBuffer, outputBuffer);
					sortLeft.start();
					sortRight.start();
					merger.start();
				}
				boolean left = false;
				while (running) {
					try {
						if (left) {
							sortLeft.inputBuffer.put(inputBuffer.get());
						} else {
							sortRight.inputBuffer.put(inputBuffer.get());
						}
						left = !left;
					} catch (final StoppException e) {
						sortLeft.inputBuffer.stopp();
						sortRight.inputBuffer.stopp();
						running = false;
					}
				}
			}
		}, "MergeSort").start();
	}

	public Buffer<T> getOutputBuffer() {
		return outputBuffer;
	}

}
