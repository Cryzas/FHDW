package application;

import lockAndBuffer.Buffer;

public class Manager {

	public static Manager create(Buffer<Integer> buffer){
		return new Manager(buffer);
	}
	private int numberOfProducers = 0;
	private int numberOfConsumers = 0;
	private final Buffer<Integer> buffer;
	private final ProducerConsumerObserver observer;

	public Manager(Buffer<Integer> buffer) {
		this.buffer = buffer;
		this.observer = ProducerConsumerObserver.create(buffer);
		this.observer.observe();
	}
	synchronized public void signalFinished() {
		this.numberOfProducers  = this.numberOfProducers - 1;
		if (numberOfProducers == 0){
			this.stoppConsumers();
		}
	}
	private void stoppConsumers() {
		for(int i = 0; i < this.numberOfConsumers; i++){
			this.buffer.stopp();
		}
		this.observer.stopp();
	}
	public void createAndStartProducer() {
		ValueProducer producer = ValueProducer.create(buffer, this);
		numberOfProducers++;
		producer.produce();
	}
	public void createAndStartConsumer() {
		ValueConsumer consumer = ValueConsumer.create(buffer);
		numberOfConsumers++;
		consumer.consume();
	}

}
class ProducerConsumerObserver {

	private static final String Observer = "Observer";
	protected static final long ObserverInterval = 333;

	public static ProducerConsumerObserver create(Buffer<Integer> buffer) {
		return new ProducerConsumerObserver(buffer);
	}

	private final Buffer<Integer> buffer;
	private boolean running = true;
	
	public ProducerConsumerObserver(Buffer<Integer> buffer) {
		this.buffer = buffer;
	}
	public void observe() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(running) {
					synchronized (this) {
						try {
							this.wait(ObserverInterval);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(buffer.size());
				}
			}
		}, Observer).start();
	}
	synchronized public void stopp() {
		this.running = false;
	}

}
