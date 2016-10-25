package lockAndBuffer;

public class Lock implements AbstractLock {

	private boolean locked;

	/**
	 * Creates a new Lock with the lock-flag set to the value of the parameter.
	 */
	public Lock(boolean locked) {
		this.locked = locked;
	}

	synchronized public void lock() {
		while (this.locked) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.locked = true;
	}

	synchronized public void unlock() {
		this.locked = false;
		this.notify();
	}
}
