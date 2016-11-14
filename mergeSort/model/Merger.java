package model;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class Merger<T extends Comparable<T>> {

	private final Buffer<T> inputBuffer;
	private final Buffer<T> inputBuffer2;
	private Buffer<T> outputBuffer;

	public Merger(Buffer<T> inputBuffer, Buffer<T> inputBuffer2, Buffer<T> outputBuffer) {
		this.inputBuffer = inputBuffer;
		this.inputBuffer2 = inputBuffer2;
		this.outputBuffer = outputBuffer;
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				T first = null;
				T second = null;
				try {
					first = inputBuffer.get();
				} catch (final StoppException e) {
					running = false;
					boolean running2 = true;
					while (running2) {
						try {
							outputBuffer.put(inputBuffer2.get());
						} catch (final StoppException e1) {
							running2 = false;
						}
					}
					outputBuffer.stopp();
				}
				if (running) {
					try {
						second = inputBuffer2.get();
					} catch (final StoppException e) {
						running = false;
						outputBuffer.put(first);
						boolean running2 = true;
						while (running2) {
							try {
								outputBuffer.put(inputBuffer.get());
							} catch (final StoppException e1) {
								running2 = false;
							}
						}
						outputBuffer.stopp();
					}
				}
				while (running) {
					if (first.compareTo(second) <= 0) {
						try {
							outputBuffer.put(first);
							first = inputBuffer.get();
						} catch (final StoppException e) {
							running = false;
							outputBuffer.put(second);
							boolean running2 = true;
							while (running2) {
								try {
									outputBuffer.put(inputBuffer2.get());
								} catch (final StoppException e1) {
									running2 = false;
								}
							}
							outputBuffer.stopp();
						}
					} else {
						try {
							outputBuffer.put(second);
							second = inputBuffer2.get();
						} catch (final StoppException e) {
							running = false;
							outputBuffer.put(first);
							boolean running2 = true;
							while (running2) {
								try {
									outputBuffer.put(inputBuffer.get());
								} catch (final StoppException e1) {
									running2 = false;
								}
							}
							outputBuffer.stopp();
						}
					}
				}
			}
		}, "Merger").start();
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}
}
