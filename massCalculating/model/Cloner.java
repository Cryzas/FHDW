package model;

import java.util.ArrayList;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;
import lockAndBuffer.Container;

public class Cloner<E> {

	private ArrayList<Buffer<E>> list = new ArrayList<Buffer<E>>();
	private Container<E> input;

	public Cloner(Container<E> buffer) {
		this.input = buffer;
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						E value = input.get();
						for (int i = 0; i < list.size(); i++) {
							list.get(i).put(value);
						}
					} catch (StoppException e) {
						running = false;
						for (int i = 0; i < list.size(); i++) {
							list.get(i).stopp();
						}
					} catch (ErrorInCalcException e) {
						running = false;
						for (int i = 0; i < list.size(); i++) {
							list.get(i).putCalcErrorException();
						}
					}
				}
			}
		}, "Cloner").start();

	}
	
	public Buffer<E> addNewClone() {
		Buffer<E> buffer = new Buffer<E>(10);
		this.list.add(buffer);
		return buffer;
	}

}
