package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class MergeSort<T extends Comparable<T>> {

	private Buffer<T> inputBuffer;
	private Buffer<T> outputBuffer;

	public MergeSort(Buffer<T> input) {
		this.setInputBuffer(input);
		setOutputBuffer(new Buffer<T>());
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
					t = getInputBuffer().get();
				} catch (final StoppException e) {
					running = false;
					getOutputBuffer().stopp();
				}
				if (running) {
					try {
						sortRight.getInputBuffer().put(getInputBuffer().get());
						sortLeft.getInputBuffer().put(t);
					} catch (final StoppException e) {
						running = false;
						getOutputBuffer().put(t);
						getOutputBuffer().stopp();
					}
				}
				if (running) {
					final Merger<T> merger = new Merger<T>(sortLeft.getOutputBuffer(), sortRight.getOutputBuffer(),
							getOutputBuffer());
					sortLeft.start();
					sortRight.start();
					merger.start();
				}
				boolean left = false;
				while (running) {
					try {
						if (left) {
							sortLeft.getInputBuffer().put(getInputBuffer().get());
						} else {
							sortRight.getInputBuffer().put(getInputBuffer().get());
						}
						left = !left;
					} catch (final StoppException e) {
						sortLeft.getInputBuffer().stopp();
						sortRight.getInputBuffer().stopp();
						running = false;
					}
				}
			}
		}, "MergeSort").start();
	}

	public Buffer<T> getOutputBuffer() {
		return outputBuffer;
	}

	public void setOutputBuffer(Buffer<T> outputBuffer) {
		this.outputBuffer = outputBuffer;
	}

	public Buffer<T> getInputBuffer() {
		return inputBuffer;
	}

	public void setInputBuffer(Buffer<T> inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

}
