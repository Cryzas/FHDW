package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;
import model.MergeSort;
import model.Sorter;

public class testMerge {

	@Test
	public void test2() throws StoppException {
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
	public void test0() throws StoppException {
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
	public void test1() throws StoppException {
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
	public void test6() throws StoppException {
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
	
	@Test
	public void testListRandom() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 1000; i++) {
			list.add(i);
		}
		Sorter<Integer> sorter = new Sorter<Integer>();
		Collections.shuffle(list);
		List<Integer> sortedList = sorter.mergeSort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}

}
