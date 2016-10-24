package model;

public class Transfer implements TransferOrTransaction {

	public static Transfer create(Account from, Account to,	long amount, String purpose) {
		return new Transfer(from, to, amount, purpose);
	}
	final private Account fromAccount;
	final private Account toAccount;
	final private long amount;
	final private String purpose;

	public Transfer(Account from, Account to, long amount, String purpose) {
		this.fromAccount = from;
		this.toAccount = to;
		this.amount = amount;
		this.purpose = purpose;
	}
	
	public Account getFromAccount() {
		return fromAccount;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public long getAmount() {
		return amount;
	}
	public String getPurpose() {
		return this.purpose;
	}
	@Override
	public void book() throws UnderLimitException {
		this.fromAccount.book(DebitEntry.create(this));
		this.toAccount.book(CreditEntry.create(this));
		System.out.println("Booking of transfer finished!");
	}

}
