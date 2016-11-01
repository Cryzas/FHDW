package arithmetic;

import lockAndBuffer.Buffer;
import lockAndBuffer.Container;
import processSystem.ArithmeticExpression;
import processSystem.ArithmeticProcess;
import processSystem.MyProcess;

public abstract class ArithemticCalculation implements ArithmeticExpression{

	ArithmeticExpression expression1;
	ArithmeticExpression expression2;
	
	public ArithemticCalculation(ArithmeticExpression expression1, ArithmeticExpression expression2){
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	public MyProcess<Integer> create(ArithmeticProcess processSystem){
		final MyProcess<Integer> process1 = expression1.create(processSystem);
		final MyProcess<Integer> process2 = expression2.create(processSystem);
		final Container<Integer> expOutBuf1 = processSystem.addExpression(process1.getOutputBuffer());
		final Container<Integer> expOutBuf2 = processSystem.addExpression(process2.getOutputBuffer());
		
		Buffer<Integer> outputBuffer = new Buffer<Integer>(10);
		processSystem.setOutput(outputBuffer);
		return this.createCalculator(expOutBuf1, expOutBuf2, outputBuffer);
	}
	
	public abstract MyProcess<Integer> createCalculator(Container<Integer> container1, Container<Integer> container2, Buffer<Integer> outputBuffer);
	
}
