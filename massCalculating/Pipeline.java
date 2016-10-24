package application;

import lockAndBuffer.Buffer;

public class Pipeline {

	private static final int Capacity = 10;

	public static void main(String[] args) {
		Buffer<Integer> buffer = Buffer.create(Capacity);
		Manager manager = Manager.create(buffer);

		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();
		manager.createAndStartProducer();

		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
		manager.createAndStartConsumer();
	}
}
