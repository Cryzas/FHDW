package application;

import java.util.Random;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public class ValueConsumer {
		
	protected static final float WaitFactor = 100;
	private static final String Consumer = "Consumer";

	public static ValueConsumer create(Buffer<Integer> source){
		return new ValueConsumer(source);
	}
	private final Buffer<Integer> source;
	private final Random random;
	private int counter;
	
	private ValueConsumer(Buffer<Integer> source) {
		this.source = source;
		this.random = new Random();
		this.counter = 0;
	}

	public void consume() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						synchronized(this) {
							this.wait((long) (random.nextFloat() * WaitFactor + 1));
						}
						ValueConsumer.this.source.get();
						counter = counter + 1;
					} catch (StoppException | InterruptedException e) {
						running = false;
					}	
				}
				System.out.println(Consumer + ": " + counter);
			}
		}, Consumer).start();
	}

}
