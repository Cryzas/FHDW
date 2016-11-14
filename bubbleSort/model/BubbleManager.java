package model;

import java.util.ArrayList;
import java.util.Collection;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class BubbleManager<T extends Comparable<T>> {

	Buffer<T> outputBuffer;
	private final Collection<BubbleProcess<T>> processList = new ArrayList<BubbleProcess<T>>();

	public BubbleManager() {
		outputBuffer = new Buffer<T>();
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}

	/**
	 * starts a new Process which sorts the buffer and logs the process
	 * automatically in the manager
	 */
	public void startNew(final Buffer<T> input) {
		final BubbleProcess<T> process = new BubbleProcess<T>(input, this);
		process.start();
		logIn(process);
	}

	/**
	 * logs the given process in to the list
	 */
	public synchronized void logIn(final BubbleProcess<T> process) {
		processList.add(process);
	}

	/**
	 * logs the given process out from the list
	 */
	public synchronized void logOut(final BubbleProcess<T> process) {
		processList.remove(process);
		if (processList.isEmpty()) {
			boolean running = true;
			while (running) {
				try {
					outputBuffer.put(process.getOutputBuffer().get());
				} catch (final StoppException e) {
					outputBuffer.stopp();
					running = false;
				}
			}
		}
	}

}
