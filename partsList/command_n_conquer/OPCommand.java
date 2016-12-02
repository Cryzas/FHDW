package command_n_conquer;

import model.QuantifiedComponent;

public class OPCommand {

	Buffer<OPCommand> returnBuffer;
	private QuantifiedComponent myComponent;
	Throwable t = null;
	Integer result = null;

	public OPCommand(QuantifiedComponent current, Buffer<OPCommand> calls) {
		this.returnBuffer = calls;
		this.myComponent = current;
	}

	public synchronized void execute() {
		try {
			result = myComponent.getOverallPriceParallel();
			System.out.println("Price of "+myComponent.getQuantity() +" "+ myComponent.getComponent().getName() + ": " + result);
			returnBuffer.put(this);
		} finally {
			this.notify();
		}
	}

	public synchronized int getResult() {
		while (result == null)
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return result;
	}

}
