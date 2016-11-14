package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;
import model.MergeSort;

public class testMerge {

	@Test
	public void test() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(111);
		buffer.put(37);
		buffer.stopp();
		MergeSort<Integer> mergeSort = new MergeSort<Integer>(buffer);
		mergeSort.start();
		assertEquals(new Integer(37), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(111), mergeSort.getOutputBuffer().get());
	}

	@Test
	public void test2() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.stopp();
		MergeSort<Integer> mergeSort = new MergeSort<Integer>(buffer);
		mergeSort.start();
		try {
			mergeSort.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
		}

	}
	
	@Test
	public void test3() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(25);
		buffer.stopp();
		MergeSort<Integer> mergeSort = new MergeSort<Integer>(buffer);
		mergeSort.start();
		assertEquals(new Integer(25), mergeSort.getOutputBuffer().get());
		try {
			mergeSort.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
		}

	}
	
	@Test
	public void test4() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(111);
		buffer.put(37);
		buffer.put(15);
		buffer.put(1);
		buffer.stopp();
		MergeSort<Integer> mergeSort = new MergeSort<Integer>(buffer);
		mergeSort.start();
		assertEquals(new Integer(1), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(15), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(37), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(111), mergeSort.getOutputBuffer().get());
	}
	
	@Test
	public void test5() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		MergeSort<Integer> mergeSort = new MergeSort<Integer>(buffer);
		mergeSort.start();
		buffer.put(111);
		buffer.put(17);
		buffer.put(37);
		buffer.put(15);
		buffer.put(1);
		buffer.put(25);
		buffer.stopp();
		assertEquals(new Integer(1), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(15), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(17), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(25), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(37), mergeSort.getOutputBuffer().get());
		assertEquals(new Integer(111), mergeSort.getOutputBuffer().get());
	}

}
