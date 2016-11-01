package arithmetic;

import lockAndBuffer.Constant;
import model.ArithmeticProcess;
import model.Const;
import model.MyProcess;

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
