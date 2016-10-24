package model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Transaction implements TransferOrTransaction {

	private final List<Transfer> transfers;
	
	public static Transaction create() {
		return new Transaction();
	}

	private Transaction() {
		this.transfers = new LinkedList<Transfer>();
	}
	public void addTransfer(final Transfer transfer) {
		this.transfers.add(transfer);
	}
	public List<Transfer> getTransfers() {
		return Collections.unmodifiableList(this.transfers);
	}
	@Override
	public void book() throws UnderLimitException {
		
		final Iterator<Transfer> i = this.transfers.iterator();
		while (i.hasNext()) {
			final Transfer current = i.next();
			current.book();
		}
		System.out.println("Booking of transaction finished!");

	}
}
