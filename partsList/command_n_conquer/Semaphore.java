package command_n_conquer;

public class Semaphore{
	
	private int counter;
	private Lock mutex = new Lock(false);
	private Lock waiting = new Lock(true);
	private Lock waitingUnlockFinished = new Lock(true);
	private int waitingCounter = 0;

	/**Creates a semaphore with the internal counter set to the value
	 * of the parameter.
	 */
	public Semaphore(int count) {
		if(count < 0)throw new Error();
		this.counter = count;
	}
	
	public void down() {
		this.mutex.lock();
		while(this.counter == 0){
			this.waitingCounter ++;
			this.mutex.unlock();
			this.waiting.lock();
			this.waitingUnlockFinished.unlock();
			this.mutex.lock();
		}
		this.counter--;
		this.mutex.unlock();
	}

	public void up() {
		this.mutex.lock();
		this.counter++;
		if(this.waitingCounter > 0){
			this.waitingCounter--;
			this.waiting.unlock();
			this.waitingUnlockFinished .lock();
		}
		this.mutex.unlock();
	}

}
