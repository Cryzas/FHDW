package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public class Sub {
	public static Sub create(Buffer<Integer> minuendBuffer, Buffer<Integer> subtrahendBuffer, Buffer<Integer> outputBuffer){
		return new Sub(minuendBuffer, subtrahendBuffer, outputBuffer);
	}
	
	private final Buffer<Integer> minuendBuffer;
	private final Buffer<Integer> subtrahendBuffer;
	private final Buffer<Integer> outputBuffer;

	private Sub(Buffer<Integer> minuendBuffer, Buffer<Integer> subtrahendBuffer, Buffer<Integer> outputBuffer){
		this.minuendBuffer = minuendBuffer;
		this.subtrahendBuffer = subtrahendBuffer;
		this.outputBuffer = outputBuffer;
	}
	
	public void sub() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						Sub.this.outputBuffer.put(Sub.this.minuendBuffer.get()-Sub.this.subtrahendBuffer.get());
					} catch (StoppException e) {
						running = false;
					}	
				}
			}
		}).start();
	}

}
