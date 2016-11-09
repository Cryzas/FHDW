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
	
	Buffer<Integer> buffer = new Buffer<Integer>();
	BubbleManager<Integer> manager = new BubbleManager<Integer>();
	
	@Test
	public void testBufferZero() throws StoppException{
		buffer.stopp();
		manager.startNew(buffer);
		try {
			manager.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
			
		}
	}
	
	@Test
	public void testBufferOne() throws StoppException{
		buffer.put(2);
		buffer.stopp();
		manager.startNew(buffer);
		assertEquals(new Integer(2), manager.getOutputBuffer().get());
	}
	
	@Test
	public void testBufferShort() throws StoppException{
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
	public void testBufferLong() throws StoppException {
		buffer.put(5);
		buffer.put(2);
		buffer.put(6);
		buffer.put(1);
		buffer.put(9);
		buffer.put(3);
		buffer.put(8);
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
		try {
			manager.getOutputBuffer().get();
			fail();
		} catch (StoppException e) {
			
		}
		
	}
	
	@Test
	public void testListZero() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}
	
	@Test
	public void testListOne() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(4);
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}
	
	@Test
	public void testListShort() throws StoppException {
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
	public void testListLong() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(3);
		list.add(2);
		list.add(5);
		list.add(6);
		list.add(5);
		list.add(7);
		list.add(2);
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}
	
	@Test
	public void testListRandom() throws StoppException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 50*Math.random(); i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		List<Integer> sortedList = manager.sort(list);
		Collections.sort(list);
		assertEquals(list, sortedList);
	}

}
