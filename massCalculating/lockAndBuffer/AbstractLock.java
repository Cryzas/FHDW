package lockAndBuffer;

public interface AbstractLock {

	/**Requests the lock. 
	 * Waits on this objekt if locked.
	 * Sets the lock-flag. */
	public void lock();
	
	/**Releases the lock, i.e. resets the lock-flag. 
	 * No further action, if no thread is waiting.
	 * If at least one thread is waiting, awakes one of them.
	 * If more than one thread is waiting,
	 * the choice is random but fair.*/
	public void unlock();	

}
