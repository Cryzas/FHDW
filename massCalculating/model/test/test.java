package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBuffer.Buffer;
import lockAndBuffer.Constant;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;
import model.Add;
import model.Div;
import model.Mul;
import model.Sub;

public class test {
	
	Buffer<Integer> first = new Buffer<Integer>(10);
	Buffer<Integer> second = new Buffer<Integer>(10);
	Buffer<Integer> output = new Buffer<Integer>(10);
		

	@Test
	public void testAdd() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
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
	public void testAddFalse() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Add add = Add.create(first, second, output);
		add.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testSub() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Sub sub = Sub.create(first, second, output);
		sub.start();
		first.put(1);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(-2), output.get());
	}

	@Test
	public void testSubFalse() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Sub sub = Sub.create(first, second, output);
		sub.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testMul() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(2);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(6), output.get());
	}

	@Test
	public void testMulFalse() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testDiv() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();
		first.put(6);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(2), output.get());
	}

	@Test
	public void testDivFalse() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testDivZero() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
		Div div = Div.create(first, second, output);
		div.start();		
		first.put(6);
		second.put(0);
		try{
		output.get();
		fail("Exception Expected");
		} catch (ErrorInCalcException e) {
			
		}
	}

	@Test
	public void testChain() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		second = new Buffer<Integer>(10);
		output = new Buffer<Integer>(10);
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
	public void testConstant() throws StoppException, ErrorInCalcException {
		first = new Buffer<Integer>(10);
		Constant<Integer> second = new Constant<Integer>(3);
		output = new Buffer<Integer>(10);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(2);
		assertEquals(new Integer(6), output.get());
		first.put(3);
		assertEquals(new Integer(9), output.get());
	}

}