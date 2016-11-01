package arithmetic;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import model.Add;
import processSystem.MyProcess;

public class Addition extends ArithemticCalculation {
	
	public Addition(ArithmeticExpression summand1, ArithmeticExpression summand2){
		super(summand1,summand2);
	}

	@Override
	public MyProcess<Integer> createCalculator(Container<Integer> container1, Container<Integer> container2,
			Buffer<Integer> outputBuffer) {
		Add add = Add.create(container1, container2, outputBuffer);
		add.start();
		return add;
	}
}
