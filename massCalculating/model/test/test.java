package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBuffer.Buffer;
import lockAndBuffer.Constant;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;
import model.Add;
import model.Cloner;
import model.Div;
import model.Mul;
import model.Sub;

public class test {
	
	Buffer<Integer> first = new Buffer<Integer>(10);
	Buffer<Integer> second = new Buffer<Integer>(10);
	Buffer<Integer> output = new Buffer<Integer>(10);
	Add add = Add.create(first, second, output);
	Sub sub = Sub.create(first, second, output);
	Mul mul = Mul.create(first, second, output);
	Div div = Div.create(first, second, output);
		

	@Test
	public void testAdd() throws StoppException, ErrorInCalcException {
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
		add.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testSub() throws StoppException, ErrorInCalcException {
		sub.start();
		first.put(1);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(-2), output.get());
	}

	@Test
	public void testSubFalse() throws StoppException, ErrorInCalcException {
		sub.start();
		first.put(1);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testMul() throws StoppException, ErrorInCalcException {
		mul.start();
		first.put(2);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(6), output.get());
	}

	@Test
	public void testMulFalse() throws StoppException, ErrorInCalcException {
		mul.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testDiv() throws StoppException, ErrorInCalcException {
		div.start();
		first.put(6);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(2), output.get());
	}

	@Test
	public void testDivFalse() throws StoppException, ErrorInCalcException {
		div.start();
		first.put(6);
		second.put(5);
		first.stopp();
		second.stopp();
		assertNotEquals(new Integer(4), output.get());
	}

	@Test
	public void testDivZero() throws StoppException, ErrorInCalcException {
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
		Buffer<Integer> third = new Buffer<Integer>(10);
		Buffer<Integer> lastoutput = new Buffer<Integer>(10);
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
		Constant<Integer> second = new Constant<Integer>(3);
		Mul mul = Mul.create(first, second, output);
		mul.start();
		first.put(2);
		assertEquals(new Integer(6), output.get());
		first.put(3);
		assertEquals(new Integer(9), output.get());
	}
	
	@Test
	public void testReUse() throws StoppException, ErrorInCalcException {
		Buffer<Integer> x = new Buffer<Integer>(10);
		Buffer<Integer> y = new Buffer<Integer>(10);
		Buffer<Integer> z = new Buffer<Integer>(10);
		Buffer<Integer> output2 = new Buffer<Integer>(10);
		Buffer<Integer> erg = new Buffer<Integer>(10);
		Constant<Integer> five = new Constant<Integer>(5);
		Cloner<Integer> cloner = new Cloner<Integer>(z,2);
		Mul mul1 = Mul.create(x, y, z);
		Add add1 = Add.create(cloner.getList().get(0), five, output2);
		Mul mul2 = Mul.create(cloner.getList().get(1), output2, erg);
		cloner.start();
		mul1.start();
		add1.start();
		mul2.start();
		x.put(2);
		y.put(3);
		assertEquals(new Integer(66), erg.get());
	}
	
	@Test
	public void testReUse3() throws StoppException, ErrorInCalcException {
		Buffer<Integer> x = new Buffer<Integer>(10);
		Buffer<Integer> y = new Buffer<Integer>(10);
		Buffer<Integer> z = new Buffer<Integer>(10);
		Buffer<Integer> output2 = new Buffer<Integer>(10);
		Buffer<Integer> erg = new Buffer<Integer>(10);
		Buffer<Integer> erg2 = new Buffer<Integer>(10);
		Constant<Integer> five = new Constant<Integer>(5);
		Cloner<Integer> cloner = new Cloner<Integer>(z,3);
		Mul mul1 = Mul.create(x, y, z);
		Add add1 = Add.create(cloner.getList().get(0), five, output2);
		Mul mul2 = Mul.create(cloner.getList().get(1), output2, erg);
		Sub sub1 = Sub.create(erg, cloner.getList().get(2), erg2);
		cloner.start();
		mul1.start();
		add1.start();
		mul2.start();
		sub1.start();
		x.put(2);
		y.put(3);
		assertEquals(new Integer(60), erg2.get());
	}

}