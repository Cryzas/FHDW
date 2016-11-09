package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lockAndBufferSort.Buffer;
import lockAndBufferSort.Buffer.StoppException;
import model.BubbleManager;

public class testBubble {
	
	@Test
	public void test3() throws StoppException{
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleManager<Integer> manager = new BubbleManager<Integer>();
		buffer.put(2);
		buffer.put(4);
		buffer.put(3);
		buffer.put(5);
		buffer.stopp();
		manager.startNew(buffer);
		assertEquals(new Integer(2), manager.getOutputBuffer().get());
		assertEquals(new Integer(3), manager.getOutputBuffer().get());
		assertEquals(new Integer(4), manager.getOutputBuffer().get());
		assertEquals(new Integer(5), manager.getOutputBuffer().get());
	}
	
	@Test
	public void test4() throws StoppException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleManager<Integer> manager = new BubbleManager<Integer>();
		buffer.put(5);
		buffer.put(2);
		buffer.put(6);
		buffer.put(1);
		buffer.put(9);
		buffer.put(3);
		buffer.put(8);
		buffer.put(12);
		buffer.put(11);
		buffer.put(10);
		buffer.put(7);
		buffer.put(4);
		buffer.stopp();
		manager.startNew(buffer);
		assertEquals(new Integer(1), manager.getOutputBuffer().get());
		assertEquals(new Integer(2), manager.getOutputBuffer().get());
		assertEquals(new Integer(3), manager.getOutputBuffer().get());
		assertEquals(new Integer(4), manager.getOutputBuffer().get());
		assertEquals(new Integer(5), manager.getOutputBuffer().get());
		assertEquals(new Integer(6), manager.getOutputBuffer().get());
		assertEquals(new Integer(7), manager.getOutputBuffer().get());
		assertEquals(new Integer(8), manager.getOutputBuffer().get());
		assertEquals(new Integer(9), manager.getOutputBuffer().get());
		assertEquals(new Integer(10), manager.getOutputBuffer().get());
		assertEquals(new Integer(11), manager.getOutputBuffer().get());
		assertEquals(new Integer(12), manager.getOutputBuffer().get());
		try {
			manager.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
			
		}
		
	}
	
	@Test
	public void test5() throws StoppException {
		BubbleManager<Integer> manager = new BubbleManager<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(3);
		list.add(2);
		list.add(5);
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}
	
	@Test
	public void test6() throws StoppException {
		BubbleManager<Integer> manager = new BubbleManager<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}
	
	@Test
	public void test7() throws StoppException {
		BubbleManager<Integer> manager = new BubbleManager<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(4);
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}

}
