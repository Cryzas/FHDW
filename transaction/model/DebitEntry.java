package model;

public class DebitEntry extends Entry{

	protected DebitEntry(Transfer transfer) {
		super(transfer);
	}

	@Override
	public <T> T acceptEntryVisitor(EntryVisitor<T> visitor) {
		return visitor.handleDebitEntry(this);
	}
	
	public static DebitEntry create(Transfer transfer){
		return new DebitEntry(transfer);
	}
	

}
