package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public class BubbleManager {
	
	Buffer<Integer> outputBuffer;
	private Collection<BubbleProcess> processList = new ArrayList<BubbleProcess>();

	public BubbleManager() {
		outputBuffer = new Buffer<Integer>(100);
	}
	
	/**
	 * sorts the given @param list with bubbleSort
	 * @return the sorted List
	 */
	public List<Integer> sort(List<Integer> list){
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		for (Integer integer : list) {
			buffer.put(integer);
		}
		buffer.stopp();
		startNew(buffer);
		boolean running = true;
		ArrayList<Integer> output = new ArrayList<Integer>();
		while(running) {
			try {
				output.add(outputBuffer.get());
			} catch (StoppException | ErrorInCalcException e) {
				running = false;
			}
		}
		return output;
	}

	public Buffer<Integer> getOutputBuffer() {
		return this.outputBuffer;
	}

	/**
	 * starts a new Process which sorts the buffer
	 */
	public void startNew(Buffer<Integer> input) {
		BubbleProcess process = new BubbleProcess(input, this);
		process.start();
		logIn(process);
	}

	/**
	 * logs the given process in to the list
	 */
	public synchronized void logIn(BubbleProcess process) {
		processList.add(process);
	}

	/**
	 * logs the given process out from the list
	 */
	public synchronized void logOut(BubbleProcess process) {
		processList.remove(process);
		if (processList.isEmpty()) {
			boolean running = true;
			while(running){
				try {
					outputBuffer.put(process.getOutputBuffer().get());
				} catch (StoppException | ErrorInCalcException e) {
					outputBuffer.stopp();
					running = false;
				}
			}
		}
	}

}
