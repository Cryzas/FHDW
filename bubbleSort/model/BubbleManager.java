package model;

import lockAndBuffer.Buffer;
import lockAndBuffer.Buffer.ErrorInCalcException;
import lockAndBuffer.Buffer.StoppException;

public class BubbleManager {

	public BubbleManager() {
	}

	Buffer<Integer> outputBuffer = new Buffer<Integer>(100);

	public Buffer<Integer> startSorting(Buffer<Integer> input) {
		BubbleProcess process = new BubbleProcess(input);
		process.start();
		Buffer<Integer> buffer = new Buffer<Integer>(100);
		try {
			while (true) {
				buffer.put(process.getOutputBuffer().get());
			}
		} catch (StoppException | ErrorInCalcException e) {
			buffer.stopp();
			if (process.needsMedic()) {
				outputBuffer = startSorting(buffer);
			} else {
				outputBuffer = buffer;
			}
		}
		return outputBuffer;
	}

	public Buffer<Integer> getOutputBuffer() {
		return this.outputBuffer;
	}

}
