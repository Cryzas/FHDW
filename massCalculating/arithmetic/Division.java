package arithmetic;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import model.Div;
import processSystem.ArithmeticExpression;
import processSystem.MyProcess;

public class Division extends ArithemticCalculation {
	
	public Division(ArithmeticExpression dividend, ArithmeticExpression divisor){
		super(dividend, divisor);
	}

	@Override
	public MyProcess<Integer> createCalculator(Container<Integer> container1, Container<Integer> container2,
			Buffer<Integer> outputBuffer) {
		Div div = Div.create(container1, container2, outputBuffer);
		div.start();
		return div;
	}

}
