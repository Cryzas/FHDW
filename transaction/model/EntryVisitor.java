package model;

public interface EntryVisitor<T> {

	T handleDebitEntry(Entry debitEntry);
	T handleCreditEntry(CreditEntry creditEntry);

}
