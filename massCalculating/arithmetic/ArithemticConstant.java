package arithmetic;

import lockAndBuffer.Constant;
import processSystem.ArithmeticProcess;
import processSystem.Const;
import processSystem.MyProcess;

public class ArithemticConstant implements ArithmeticExpression {
	
	int value;
	
	public ArithemticConstant(int value){
		this.value = value;
	}

	public MyProcess<Integer> create(ArithmeticProcess processSystem) {
		Constant<Integer> container = new Constant<Integer>(value);
		processSystem.setOutput(container);
		return new Const(container);
	}

}
