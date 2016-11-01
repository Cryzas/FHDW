package arithmetic;

import lockAndBuffer.Buffer;
import model.ArithmeticProcess;
import model.MyProcess;
import model.Var;

public class Variable implements ArithmeticExpression{
	
	public Variable() {
	}
	
	@Override
	public MyProcess<Integer> create(ArithmeticProcess processSystem){
		Buffer<Integer> outputBuffer = processSystem.addVariable(this);
		processSystem.setOutput(outputBuffer);
		return new Var(outputBuffer);
	}
}
