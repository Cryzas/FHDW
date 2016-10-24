package lockAndBuffer;

public class Semaphore implements AbstractSemaphore{

	AbstractLock mutex = new Lock(false);
	AbstractLock waiting = new Lock(true);
	private int counter;
	
	/**Creates a semaphore with the internal counter set to the value
	 * of the parameter.
	 */

	
	public Semaphore(int count) {
		if (count < 0) throw new Error();
		this.counter = count;		
	}

	@Override
	public void down() {
		this.mutex.lock();
		while (this.counter == 0){
			this.mutex.unlock();
			this.waiting.lock();
			this.mutex.lock();
		}
			this.counter--;
			this.mutex.unlock();
		
	}

	@Override
	public void up() {
		this.mutex.lock();
		this.counter++;
		this.waiting.unlock();
		this.mutex.unlock();
		
	}

}
