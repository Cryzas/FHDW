package model;

public class TransferState {

	private int tryCount;
	private boolean booked;

	public TransferState() {
		this.booked = false;
		this.tryCount = 0;
	}
	
	public void tryTransfer(){
		booked = true;
		tryCount += 1;
	}

	public int getTryCount() {
		return tryCount;
	}

	public boolean isBooked() {
		return booked;
	}
	
	
}
