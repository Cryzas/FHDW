package model;

/** An entry (debit or credit) for an account, typically result of a booking process. */
public abstract class Entry {
	
	Transfer transfer;
	
	protected Entry(Transfer transfer){
		this.transfer = transfer;
	}
	

	abstract public <T> T acceptEntryVisitor(EntryVisitor<T> visitor);


	public String getPurpose() {
		return this.transfer.getPurpose();
	}
	
	public long getAmount() {
		return this.transfer.getAmount();
	}
	
}
