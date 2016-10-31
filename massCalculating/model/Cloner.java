package model;

import java.util.ArrayList;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public class Cloner<E> {

	private ArrayList<Buffer<E>> list = new ArrayList<Buffer<E>>();
	private Buffer<E> input;

	public Cloner(Buffer<E> buffer, int number) throws StoppException, ErrorInCalcException {
		this.input = buffer;
		for (int i = 0; i < number; i++) {
			list.add(new Buffer<E>(buffer.size()));
		}
	}

	public void duplicate() throws StoppException, ErrorInCalcException {
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
					} catch (ErrorInCalcException e) {
						running = false;
					}
				}
			}
		}, "Cloner").start();

	}

	public ArrayList<Buffer<E>> getList() {
		return list;
	}

}
