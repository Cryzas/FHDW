package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class BubbleManager<T extends Comparable<T>> {

	Buffer<T> outputBuffer;
	private final Collection<BubbleProcess<T>> processList = new ArrayList<BubbleProcess<T>>();

	public BubbleManager() {
		outputBuffer = new Buffer<T>();
	}

	/**
	 * sorts the given @param list with bubbleSort
	 * 
	 * @return the sorted List
	 */
	public List<T> sort(final List<T> list) {
		outputBuffer = new Buffer<T>();
		final Buffer<T> buffer = new Buffer<T>();
		// copy list in buffer
		for (final T t : list) {
			buffer.put(t);
		}
		buffer.stopp();
		// start sorting
		startNew(buffer);
		boolean running = true;
		final ArrayList<T> output = new ArrayList<T>();
		// copy sorted buffer in output list
		while (running) {
			try {
				output.add(outputBuffer.get());
			} catch (final StoppException e) {
				running = false;
			}
		}
		return output;
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
