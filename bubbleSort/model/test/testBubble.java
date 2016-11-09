package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;
import model.BubbleManager;
import model.BubbleProcess;

public class testBubble {

	@Test
	public void test() throws StoppException, ErrorInCalcException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleProcess process = new BubbleProcess(buffer);
		process.start();
		buffer.put(3);
		buffer.put(2);
		buffer.put(5);
		buffer.put(4);
		buffer.stopp();
		assertEquals(new Integer(2), process.getOutputBuffer().get());
		assertEquals(new Integer(3), process.getOutputBuffer().get());
		assertEquals(new Integer(4), process.getOutputBuffer().get());
		assertEquals(new Integer(5), process.getOutputBuffer().get());
	}

	@Test
	public void test1() throws StoppException, ErrorInCalcException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleProcess process = new BubbleProcess(buffer);
		process.start();
		buffer.stopp();
		try {
			assertEquals(new Integer(2), process.getOutputBuffer().get());
			fail();
		} catch (StoppException e) {
		}
	}
	
	@Test
	public void test2() throws StoppException, ErrorInCalcException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleProcess process = new BubbleProcess(buffer);
		process.start();
		buffer.put(2);
		buffer.stopp();
		assertEquals(new Integer(2), process.getOutputBuffer().get());
	}
	
	@Test
	public void test3() throws StoppException, ErrorInCalcException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleManager manager = new BubbleManager();
		buffer.put(5);
		buffer.put(4);
		buffer.put(3);
		buffer.put(2);
		buffer.stopp();
		manager.startSorting(buffer);
		assertEquals(new Integer(2), manager.getOutputBuffer().get());
		assertEquals(new Integer(3), manager.getOutputBuffer().get());
		assertEquals(new Integer(4), manager.getOutputBuffer().get());
		assertEquals(new Integer(5), manager.getOutputBuffer().get());
	}
	
	@Test
	public void test4() throws StoppException, ErrorInCalcException {
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		BubbleManager manager = new BubbleManager();
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
		manager.startSorting(buffer);
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
		} catch (StoppException | ErrorInCalcException e) {
			
		}
	}

}
