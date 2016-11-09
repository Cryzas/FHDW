package lockAndBufferSort;

import java.util.LinkedList;
import java.util.List;

public class Buffer<E> {

	@SuppressWarnings("serial")
	public static class StoppException extends Exception {
	}

	private abstract class BufferEntry<T> {
		abstract T getWrapped() throws StoppException;
	}

	private class Stopp<T> extends BufferEntry<T> {
		Stopp() {
		}

		@Override
		T getWrapped() throws StoppException {
			throw new StoppException();
		}
	}

	private class Wrapped<T> extends BufferEntry<T> {
		final private T wrapped;

		Wrapped(T toBeWrapped) {
			this.wrapped = toBeWrapped;
		}

		@Override
		public T getWrapped() {
			return this.wrapped;
		}
	}

	List<BufferEntry<E>> implementingList;

	public Buffer() {
		this.implementingList = new LinkedList<BufferEntry<E>>();
	}

	synchronized public void put(E value) {
		this.implementingList.add(new Wrapped<E>(value));
		this.notify();
	}

	synchronized public E get() throws StoppException {
		while (this.isEmpty())
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		E result = this.implementingList.get(0).getWrapped();
		this.implementingList.remove(0);
		return result;
	}

	synchronized public void stopp() {
		this.implementingList.add(new Stopp<E>());
		this.notify();
	}

	private boolean isEmpty() {
		return this.implementingList.isEmpty();
	}
}
