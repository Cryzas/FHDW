package command_n_conquer;


public class Buffer<E> {
	public static <E> Buffer<E> create(int capacity) {
		return new Buffer<E> (capacity);
	}
	@SuppressWarnings("serial")
	public static class StoppException extends Exception {}

	private abstract class BufferEntry<T> {
		abstract T getWrapped() throws StoppException;
	}
	private class Stopp<T> extends BufferEntry<T>{
		Stopp(){}
		@Override
		T getWrapped() throws StoppException {
			throw new StoppException();
		}
	}
	private class Wrapped<T> extends BufferEntry<T> {
		final private T wrapped;
		Wrapped(T toBeWrapped){
			this.wrapped = toBeWrapped;
		}
		@Override
		public T getWrapped() {
			return this.wrapped;
		}
	}
	
	final private Semaphore openForGets;
	final private Semaphore openForPuts;
	final private Semaphore mutex;
	final private Object[] myBuffer;
	private int first;
	private int behindLast;
	private int capacity;
	
	public Buffer(int capacity){
		this.capacity = capacity <= 0 ? 1 : capacity;
		this.openForGets = new Semaphore(0);
		this.openForPuts = new Semaphore(this.capacity);
		this.mutex = new Semaphore(1);
		this.myBuffer = new Object[capacity <= 0 ? 1 : capacity];
		this.first = 0;
		this.behindLast = 0;
	}
	
	public void put(E value) {
		this.put(new Wrapped<E>(value));
	}
	private void put(BufferEntry<E> value) {
		this.openForPuts.down();
		this.mutex.down();
		this.addNextEntry(value);
		this.mutex.up();
		this.openForGets.up();
	}
	public E get() throws StoppException {
		this.openForGets.down();
		this.mutex.down();
		E result = null;
		try {
			result = this.getNextEntry().getWrapped();
		} finally {
			this.mutex.up();
			this.openForPuts.up();
		}
		return result;
	}
	private void addNextEntry(Buffer<E>.BufferEntry<E> wrapped) {
		this.myBuffer[this.behindLast] = wrapped;
		this.behindLast = (this.behindLast + 1) % this.capacity;
	}
	private Buffer<E>.BufferEntry<E> getNextEntry() {
		@SuppressWarnings("unchecked")
		BufferEntry<E> result = (Buffer<E>.BufferEntry<E>) this.myBuffer[this.first];
		this.first = (this.first + 1) % this.capacity;
		return result;
	}
	public void stopp(){
		this.put(new Stopp<E>());
	}

	public int size(){
		this.mutex.down();
		int result = this.first <= this.behindLast 
						? this.behindLast - this.first
						: this.behindLast + (this.capacity - this.first)				;
		this.mutex.up();
		return result;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int listSize) {
		this.capacity = listSize;
	}
}
