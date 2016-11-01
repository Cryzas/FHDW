package arithmetic;

import model.ArithmeticProcess;
import model.MyProcess;

public interface ArithmeticExpression {
	
	public MyProcess<Integer> create(ArithmeticProcess processSystem);

}
