package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;
import model.Add;
import model.Constant;
import model.Div;
import model.Mul;
import model.Sub;

public class test {

	@Test
	public void testAdd() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Add add = Add.create(first, second, output);
		add.start();
		first.put(1);
		second.put(3);
		assertEquals(new Integer(4), output.get());

		first.stopp();
		second.stopp();
		try {
			output.get();
			fail("StoppException exepcted");
		} catch (final StoppException e) {
			// OK
		}
    }
	
	@Test
	public void testAddFalse() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Add add = Add.create(first, second, output);
		add.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}
	
	@Test
	public void testSub() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Sub sub = Sub.create(first, second, output);
		sub.start();
		first.put(1);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(-2), output.get());
	}
	
	@Test
	public void testSubFalse() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Sub sub = Sub.create(first, second, output);
		sub.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}
	
	@Test
	public void testMul() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(2);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(6), output.get());
	}
	
	@Test
	public void testMulFalse() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}
	

	
	@Test
	public void testDiv() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();
		first.put(6);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(2), output.get());
	}
	
	@Test
	public void testDivFalse() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}
	
	@Test
	public void testDivZero() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();
		try {
			
		first.put(6);
		second.put(0);
		fail();
		} catch (Error e) {
			
		}
	}
	
	@Test
	public void testChain() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Buffer<Integer> third = new Buffer<Integer>(10);
		Buffer<Integer> lastoutput = new Buffer<Integer>(10);
		Sub sub = Sub.create(first, second, output);
		sub.start();
		Add add = Add.create(output, third, lastoutput);
		add.start();
		first.put(1);
		second.put(3);
		third.put(4);
		assertEquals(new Integer(2), lastoutput.get());
	}
	
	@Test
	public void testConstant() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Constant<Integer> second = new Constant<Integer>(3);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(2);
		assertEquals(new Integer(6), output.get());
		first.put(3);
		assertEquals(new Integer(9), output.get());
	}

}
