package arithmetic;

import processSystem.ArithmeticProcess;
import processSystem.MyProcess;

public interface ArithmeticExpression {
	
	public MyProcess<Integer> create(ArithmeticProcess processSystem);

}
