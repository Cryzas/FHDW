import java.util.Random;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.StoppException;

public class ValueConsumer {

	public static ValueConsumer create(Buffer<Integer> source){
		return new ValueConsumer(source);
	}
	private final Buffer<Integer> source;
	private int counter;
	
	private ValueConsumer(Buffer<Integer> source) {
		this.source = source;
		this.counter = 0;
	}

	public void consume() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						ValueConsumer.this.source.get();
						counter = counter + 1;
					} catch (StoppException e) {
						running = false;
					}	
				}
			}
		}).start();
	}

}
