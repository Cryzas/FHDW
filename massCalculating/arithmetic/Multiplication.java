package arithmetic;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import model.Mul;
import processSystem.MyProcess;

public class Multiplication extends ArithemticCalculation {
	
	public Multiplication(ArithmeticExpression factor1, ArithmeticExpression factor2){
		super(factor1,factor2);
	}

	@Override
	public MyProcess<Integer> createCalculator(Container<Integer> container1, Container<Integer> container2,
			Buffer<Integer> outputBuffer) {
		Mul mul = Mul.create(container1, container2, outputBuffer);
		mul.start();
		return mul;
	}
}
