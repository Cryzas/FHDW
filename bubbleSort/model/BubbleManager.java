package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class BubbleManager<T extends Comparable<T>> {
	
	Buffer<T> outputBuffer;
	private Collection<BubbleProcess<T>> processList = new ArrayList<BubbleProcess<T>>();

	public BubbleManager() {
		outputBuffer = new Buffer<T>();
	}
	
	/**
	 * sorts the given @param list with bubbleSort
	 * @return the sorted List
	 */
	public List<T> sort(List<T> list){
		Buffer<T> buffer = new Buffer<T>();
		for (T t : list) {
			buffer.put(t);
		}
		buffer.stopp();
		startNew(buffer);
		boolean running = true;
		ArrayList<T> output = new ArrayList<T>();
		while(running) {
			try {
				output.add(outputBuffer.get());
			} catch (StoppException e) {
				running = false;
			}
		}
		return output;
	}

	public Buffer<T> getOutputBuffer() {
		return this.outputBuffer;
	}

	/**
	 * starts a new Process which sorts the buffer
	 */
	public void startNew(Buffer<T> input) {
		BubbleProcess<T> process = new BubbleProcess<T>(input, this);
		process.start();
		logIn(process);
	}

	/**
	 * logs the given process in to the list
	 */
	public synchronized void logIn(BubbleProcess<T> process) {
		processList.add(process);
	}

	/**
	 * logs the given process out from the list
	 */
	public synchronized void logOut(BubbleProcess<T> process) {
		processList.remove(process);
		if (processList.isEmpty()) {
			boolean running = true;
			while(running){
				try {
					outputBuffer.put(process.getOutputBuffer().get());
				} catch (StoppException e) {
					outputBuffer.stopp();
					running = false;
				}
			}
		}
	}

}
