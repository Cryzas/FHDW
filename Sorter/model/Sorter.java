package model;

import java.util.ArrayList;
import java.util.List;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;

public class Sorter<T extends Comparable<T>> {
	
	public Sorter(){
		
	}
	
	/**
	 * sorts the given @param list with mergeSort
	 * 
	 * @return the sorted List
	 */
	public List<T> mergeSort(final List<T> list) {
		
		final Buffer<T> buffer = new Buffer<T>();
		// copy list in buffer
		for (final T t : list) {
			buffer.put(t);
		}
		buffer.stopp();
		// start sorting
		MergeSort<T> mergeSort = new MergeSort<T>(buffer);
		mergeSort.start();
		mergeSort.setInputBuffer(buffer);
		mergeSort.setOutputBuffer(new Buffer<T>());
		boolean running = true;
		final ArrayList<T> output = new ArrayList<T>();
		// copy sorted buffer in output list
		while (running) {
			try {
				output.add(mergeSort.getOutputBuffer().get());
			} catch (final StoppException e) {
				running = false;
			}
		}
		return output;
	}
	
	/**
	 * sorts the given @param list with bubbleSort
	 * 
	 * @return the sorted List
	 */
	public List<T> bubbleSort(final List<T> list) {
		final Buffer<T> buffer = new Buffer<T>();
		// copy list in buffer
		for (final T t : list) {
			buffer.put(t);
		}
		buffer.stopp();
		// start sorting
		BubbleManager<T> manager = new BubbleManager<T>();
		manager.startNew(buffer);
		boolean running = true;
		final ArrayList<T> output = new ArrayList<T>();
		// copy sorted buffer in output list
		while (running) {
			try {
				output.add(manager.getOutputBuffer().get());
			} catch (final StoppException e) {
				running = false;
			}
		}
		return output;
	}

}
