package application;

import java.util.Random;

import lockAndBuffer.Buffer;

public class ValueProducer {
	
	private static final long ProductionSize = 300;
	private static final long WaitFactor = 50;
	private static final String Producer = "Producer";
	public static ValueProducer create(Buffer<Integer> target, Manager manager){
		return new ValueProducer(target, manager);
	}
	private final Buffer<Integer> target;
	private final Random random;
	private Manager manager;
	private ValueProducer(Buffer<Integer> target, Manager manager) {
		this.target = target;
		this.manager = manager;
		this.random = new Random();
	}
	
	public void produce(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ProductionSize; i++){
					synchronized (this) {
						try {
							this.wait((long) (random.nextFloat() * WaitFactor + 1));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						target.put(i);
					}
				}
				manager.signalFinished();
			}
		}, Producer).start();
	}

}
