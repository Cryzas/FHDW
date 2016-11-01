package arithmetic;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import model.Sub;
import processSystem.ArithmeticExpression;
import processSystem.MyProcess;

public class Substraction extends ArithemticCalculation {
	
	public Substraction(ArithmeticExpression subtrahend, ArithmeticExpression minuend){
		super(subtrahend,minuend);
	}

	@Override
	public MyProcess<Integer> createCalculator(Container<Integer> container1, Container<Integer> container2,
			Buffer<Integer> outputBuffer) {
		Sub sub = Sub.create(container1, container2, outputBuffer);
		sub.start();
		return sub;
	}
}
