package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;
import model.Add;

public class test {

	@Test
	public void testAdd() throws StoppException {
		Buffer<Integer> first = new Buffer<Integer>(10);
		Buffer<Integer> second = new Buffer<Integer>(10);
		Buffer<Integer> output = new Buffer<Integer>(10);
		Add add = Add.create(first, second, output);
		add.add();
		first.put(1);
		second.put(3);
		first.stopp();
		second.stopp();
		assertEquals(new Integer(4), output.get());
	}

}
