package viewModel;

import java.util.Iterator;

import javax.swing.ListModel;

import model.Account;
import model.AccountObserver;
import model.Entry;

public class AccountView implements AccountObserver {
	
	private static final String SaldoOpenBracket = " (";
	private static final String SaldoCloseBracket = ")";

	public static AccountView create(Account account, AccountViewManager manager){
		return new AccountView(account, manager);
	}

	final private Account account;
	final private AccountViewManager manager;

	public AccountView(Account account, AccountViewManager manager) {
		this.account = account;
		this.manager = manager;
		this.account.register(this);
	}
	Account getAccount() {
		return this.account;
	}
	public String getName() {
		return this.account.getName();
	}
	public String toString() {
		return this.account.getName() + SaldoOpenBracket + this.account.getBalance() + SaldoCloseBracket;
	}
	public ListModel<EntryView> getAccountEntries() {
		SpecialDefaultListModel<EntryView> result = new SpecialDefaultListModel<EntryView>();
		Iterator<Entry> entryIterator = account.getAccountEntries().iterator();
		while (entryIterator.hasNext()) result.addElement(EntryView.create(entryIterator.next()));
		return result;
	}
	@Override
	public void update() {
		this.manager.handleAccountUpdate(this);
	}
	public void release() {
		this.account.deregister(this);
	}
	public boolean isFor(Account account) {
		return this.account.equals(account);
	}

}
