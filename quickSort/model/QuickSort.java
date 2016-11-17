package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class QuickSort<T extends Comparable<T>> {

	private Buffer<T> inputBuffer;
	private T representant;
	private Buffer<T> outputBuffer;

	public QuickSort(Buffer<T> input) {
		this.setInputBuffer(input);
		setOutputBuffer(new Buffer<T>());
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final QuickSort<T> sortLeft = new QuickSort<T>(new Buffer<>());
				final QuickSort<T> sortRight = new QuickSort<T>(new Buffer<>());
				boolean running = true;
				try {
					representant = inputBuffer.get();
				} catch (final StoppException e) {
					running = false;
					outputBuffer.stopp();
				}
				if (running) {
					try {
						T t = inputBuffer.get();
						if (t.compareTo(representant) <= 0) {
							sortLeft.getInputBuffer().put(t);
						} else {
							sortRight.getInputBuffer().put(t);
						}
					} catch (final StoppException e) {
						running = false;
						outputBuffer.put(representant);
						outputBuffer.stopp();
					}
					if (running) {
						final QuickSortMerge<T> merger = new QuickSortMerge<T>(sortLeft.getOutputBuffer(), representant,
								sortRight.getOutputBuffer(), outputBuffer);
						sortLeft.start();
						sortRight.start();
						merger.start();
					}
					while (running) {
						try {
							T t = inputBuffer.get();
							if (t.compareTo(representant) <= 0) {
								sortLeft.getInputBuffer().put(t);
							} else {
								sortRight.getInputBuffer().put(t);
							}
						} catch (final StoppException e) {
							running = false;
							sortLeft.getInputBuffer().stopp();
							sortRight.getInputBuffer().stopp();
						}
					}
				}
			}
		}, "QuickSort").start();
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
