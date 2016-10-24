package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public class Mul {
	public static Mul create(Buffer<Integer> summandBuffer1, Buffer<Integer> summandBuffer2, Buffer<Integer> outputBuffer){
		return new Mul(summandBuffer1, summandBuffer2, outputBuffer);
	}
	
	private final Buffer<Integer> summandBuffer1;
	private final Buffer<Integer> summandBuffer2;
	private final Buffer<Integer> outputBuffer;

	private Mul(Buffer<Integer> summandBuffer1, Buffer<Integer> summandBuffer2, Buffer<Integer> outputBuffer){
		this.summandBuffer1 = summandBuffer1;
		this.summandBuffer2 = summandBuffer2;
		this.outputBuffer = outputBuffer;
	}
	
	public void Mul() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						Mul.this.outputBuffer.put(Mul.this.summandBuffer1.get()+Mul.this.summandBuffer2.get());
					} catch (StoppException e) {
						running = false;
					}	
				}
			}
		}).start();
	}

}
