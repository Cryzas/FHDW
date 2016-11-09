package lockAndBufferSort;

public interface AbstractSemaphore {
	/**
	 * The counter is decremented. If the counter is negative, the calling
	 * thread must wait here.
	 */
	public void down();

	/**
	 * Increments the internal counter . If some threads are waiting here, one
	 * of them is chosen randomly but fair and set alive again.
	 */
	public void up();
}
