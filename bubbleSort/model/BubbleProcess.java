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
		this.outputBuffer = new Buffer<T>();
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
				// if first was stop the buffer was empty and second is not
				// needed
				if (running) {
					try {
						second = inputBuffer.get();
					} catch (StoppException e) {
						running = false;
						// second is stop so one item in buffer which is put in
						// output, because it is sorted
						outputBuffer.put(first);
						outputBuffer.stopp();
					}
				}

				while (running) {
					try {
						if (first.compareTo(second) <= 0) {
							// sorted
							// put first in output, swap second to first and get
							// new second
							outputBuffer.put(first);
							first = second;
							second = inputBuffer.get();
						} else {
							// swap Items
							outputBuffer.put(second);
							// get new second
							second = inputBuffer.get();
							// if no next process was started, this process
							// calls the manager to start a new process
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
				// log out from the manager
				manager.logOut(BubbleProcess.this);
			}
		}, "BubbleProcess").start();
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}

}
