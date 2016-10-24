package model;

public class CreditEntry extends Entry{

	protected CreditEntry(Transfer transfer) {
		super(transfer);
	}

	@Override
	public <T> T acceptEntryVisitor(EntryVisitor<T> visitor) {
		return visitor.handleCreditEntry(this);
	}
	
	public static CreditEntry create(Transfer transfer){
		return new CreditEntry(transfer);
	}

}
