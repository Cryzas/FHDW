package model;

import java.util.ArrayList;

import lockAndBuffer.Buffer;
import lockAndBuffer.Constant;
import lockAndBuffer.Container;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public class Cloner<E> {

	private ArrayList<Container<E>> list = new ArrayList<Container<E>>();
	private Container<E> input;

	public Cloner(Container<E> container) {
		this.input = container;
	}

	public void start() {
		if (input instanceof Buffer) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				while (running) {
						try {
						E value = input.get();
						for (int i = 0; i < list.size(); i++) {
							((Buffer<E>) list.get(i)).put(value);
						}
					} catch (StoppException e) {
						running = false;
						for (int i = 0; i < list.size(); i++) {
							((Buffer<E>) list.get(i)).stopp();
						}
					} catch (ErrorInCalcException e) {
						running = false;
						for (int i = 0; i < list.size(); i++) {
							((Buffer<E>) list.get(i)).putCalcErrorException();
						}
					}
				}
			}
		}, "Cloner").start();
		}

	}
	
	public Container<E> addNewClone() {
		if (input instanceof Buffer) {
			Buffer<E> buffer = new Buffer<E>(10);
			this.list.add(buffer);
			return buffer;
		} else if(input instanceof Constant){
			Constant<E> constant = new Constant<E>(((Constant<E>)(input)).get());
			this.list.add(constant);
			return constant;
		}
		return null;
	}

}
