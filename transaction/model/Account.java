package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** A simple account that possesses a list of account entries, 
 *  the sum of which constitutes the account's >balance>.
 */
public class Account {
	
	/** The balance of all accounts shall be greater or equal than this limit. */
	public static final long UniversalAccountLimit = -1000; 

	public static Account create(String name) {
		return new Account(name);
	}

	private String name;
	private long balance;
	List<Entry> accountEntries;
	private List<AccountObserver> observers;

	public Account(String name) {
		this.name = name;
		this.balance = 0;
		this.accountEntries = new LinkedList<Entry>();
		this.observers = new LinkedList<AccountObserver>();
		
	}
	public long getBalance() {
		return this.balance;
	}
	public String getName() {
		return this.name;
	}
	public List<Entry> getAccountEntries() {
		return this.accountEntries;
	}
	public void register(AccountObserver observer) {
		if (this.observers.contains(observer)) return;
		this.observers.add(observer);
	}
	public void deregister(AccountObserver observer) {
		this.observers.remove(observer);
	}
	private void notifyObservers() {
		Iterator<AccountObserver> currentObservers = this.observers.iterator();
		while (currentObservers.hasNext()) currentObservers.next().update();
	}

	public long entrySize() {
		return this.accountEntries.size();
	}
	
	
	public void book(Entry entry) throws UnderLimitException {
		if((Account.this.balance -= entry.getAmount()) < UniversalAccountLimit || (Account.this.balance += entry.getAmount()) < UniversalAccountLimit){
			throw new UnderLimitException();
		}
		this.accountEntries.add(entry);
		entry.acceptEntryVisitor(new EntryVisitor<Void>() {

			@Override
			public Void handleDebitEntry(Entry debitEntry) {
				Account.this.balance -= entry.getAmount();
				return null;
			}

			@Override
			public Void handleCreditEntry(CreditEntry creditEntry) {
				Account.this.balance += entry.getAmount();
				return null;
			}
		});
		this.notifyObservers();
	}
}
