package arithmetic;

import lockAndBuffer.Buffer;
import processSystem.ArithmeticProcess;
import processSystem.MyProcess;
import processSystem.Var;

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
