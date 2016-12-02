package model.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;
import model.QuickSort;
import model.Sorter;

public class testQuick {

	@Test
	public void test2() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(111);
		buffer.put(37);
		buffer.stopp();
		QuickSort<Integer> quickSort = new QuickSort<Integer>(buffer);
		quickSort.start();
		assertEquals(new Integer(37), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(111), quickSort.getOutputBuffer().get());
	}

	@Test
	public void test0() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.stopp();
		QuickSort<Integer> quickSort = new QuickSort<Integer>(buffer);
		quickSort.start();
		try {
			quickSort.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
		}

	}
	
	@Test
	public void test1() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(25);
		buffer.stopp();
		QuickSort<Integer> quickSort = new QuickSort<Integer>(buffer);
		quickSort.start();
		assertEquals(new Integer(25), quickSort.getOutputBuffer().get());
		try {
			quickSort.getOutputBuffer().get();
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
		QuickSort<Integer> quickSort = new QuickSort<Integer>(buffer);
		quickSort.start();
		assertEquals(new Integer(1), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(15), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(37), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(111), quickSort.getOutputBuffer().get());
	}
	
	@Test
	public void test6() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>();
		buffer.put(111);
		buffer.put(17);
		buffer.put(37);
		buffer.put(15);
		buffer.put(1);
		buffer.put(25);
		buffer.stopp();
		QuickSort<Integer> quickSort = new QuickSort<Integer>(buffer);
		quickSort.start();
		assertEquals(new Integer(1), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(15), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(17), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(25), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(37), quickSort.getOutputBuffer().get());
		assertEquals(new Integer(111), quickSort.getOutputBuffer().get());
	}
	
	@Test
	public void testListRandom() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 1000; i++) {
			list.add(i);
		}
		Sorter<Integer> sorter = new Sorter<Integer>();
		Collections.shuffle(list);
		List<Integer> sortedList = sorter.quickSort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}

}
