package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public class Add {
	
	public static Add create(Buffer<Integer> summandBuffer1, Buffer<Integer> summandBuffer2, Buffer<Integer> outputBuffer){
		return new Add(summandBuffer1, summandBuffer2, outputBuffer);
	}
	
	private final Buffer<Integer> summandBuffer1;
	private final Buffer<Integer> summandBuffer2;
	private final Buffer<Integer> outputBuffer;

	private Add(Buffer<Integer> summandBuffer1, Buffer<Integer> summandBuffer2, Buffer<Integer> outputBuffer){
		this.summandBuffer1 = summandBuffer1;
		this.summandBuffer2 = summandBuffer2;
		this.outputBuffer = outputBuffer;
	}
	
	public void add() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						Add.this.outputBuffer.put(Add.this.summandBuffer1.get()+Add.this.summandBuffer2.get());
					} catch (StoppException e) {
						running = false;
					}	
				}
			}
		}).start();
	}
}
