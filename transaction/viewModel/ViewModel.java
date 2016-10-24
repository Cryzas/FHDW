package viewModel;

import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import model.Account;
import model.AccountException;
import model.AccountManager;
import model.UnderLimitException;

public class ViewModel implements AccountViewManager {
	
	public static ViewModel create(AccountTransactionFacadeView view) {
		return new ViewModel(view);
	}

	private SpecialDefaultListModel<AccountView> myAccounts;
	private SpecialDefaultListModel<AccountView> otherAccounts;
	private AccountView selectedAccount;
	private ListModel<EntryView> currentEntries;
	private DefaultListModel<TransferOrTransactionView> pendingTransfersAndOrTransactions;
	private TransactionView selectedTransaction;
	private DefaultListModel<TransferOrTransactionView> currentTransactionDetails;
	private AccountTransactionFacadeView view;

	private ViewModel (AccountTransactionFacadeView view) {
		this.view = view;
		this.myAccounts = new SpecialDefaultListModel<AccountView>();
		this.otherAccounts = new SpecialDefaultListModel<AccountView>();
		this.currentEntries = new DefaultListModel<EntryView>();
		this.pendingTransfersAndOrTransactions = new SpecialDefaultListModel<TransferOrTransactionView>();
		this.currentTransactionDetails = new SpecialDefaultListModel<TransferOrTransactionView>();
	}
	public ListModel<AccountView> getMyAccountList() {
		return this.myAccounts;
	}
	public ListModel<AccountView> getOtherAccountList() {
		return this.otherAccounts;
	}
	public ListModel<EntryView> getCurrentAccountEntries() {
		return this.currentEntries;
	}
	public ListModel<TransferOrTransactionView> getPendingTransfersAndorTransactions() {
		return this.pendingTransfersAndOrTransactions;
	}
	public void createAccount(String name) throws AccountException {
		Account newAccount = AccountManager.getTheAccountManager().create(name);
		this.myAccounts.addElement(AccountView.create(newAccount, this));
	}
	public void findAccount(String name) throws AccountException {
		Account foundAccount = AccountManager.getTheAccountManager().find(name);
		if (this.containsInOtherAccounts(foundAccount)) throw new AccountAlreadyShownException(name);
		this.otherAccounts.addElement(AccountView.create(foundAccount, this));
	}
	private boolean containsInOtherAccounts(Account account) {
		Enumeration<AccountView> otherAccountsEnumeration = this.otherAccounts.elements();
		while (otherAccountsEnumeration.hasMoreElements()){
			AccountView current = otherAccountsEnumeration.nextElement();
			if (current.isFor(account)) return true;
		}
		return false;
	}
	public void clearOtherAccounts() {
		Enumeration<AccountView> otherAccountsEnumeration = this.otherAccounts.elements();
		while (otherAccountsEnumeration.hasMoreElements()){
			AccountView current = otherAccountsEnumeration.nextElement();
			current.release();
		}
		this.otherAccounts.clear();
	}
	public void changeAccountSelection(AccountView selectedAccount) {
		this.selectedAccount = selectedAccount;
		this.currentEntries = this.selectedAccount.getAccountEntries();
		this.view.updateEntriesOfSelectedAccount();
	}
	public void changeTransactionSelection(TransactionView selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
		this.currentTransactionDetails = this.selectedTransaction.getDetails();
	}
	public void handleAccountUpdate(AccountView accountView) {
		int index = 0;
		Enumeration<AccountView> myAccountsEnumeration = this.myAccounts.elements();
		while (myAccountsEnumeration.hasMoreElements()) {
			AccountView current = myAccountsEnumeration.nextElement();
			if (current.equals(accountView)){
				this.myAccounts.fireEntryChanged(index);
				break;
			}
			index++;
		}
		index = 0;
		Enumeration<AccountView> otherAccountsEnumeration = this.otherAccounts.elements();
		while (otherAccountsEnumeration.hasMoreElements()) {
			AccountView current = otherAccountsEnumeration.nextElement();
			if (current.equals(accountView)){
				this.otherAccounts.fireEntryChanged(index);
				break;
			}
			index++;
		}
		if (this.selectedAccount != null && this.selectedAccount.equals(accountView)) 
			this.changeAccountSelection(this.selectedAccount);
	}
	public void createTransfer(AccountView from, AccountView to, long amount, String purpose) {
		TransferView newTransaction = TransferView.create(from,to,amount,purpose);
		this.pendingTransfersAndOrTransactions.addElement(newTransaction);
	}
	public void createTransferInTransaction(AccountView from, AccountView to, long amount, String purpose, TransactionView transaction) {
		transaction.addTransfer(from,to,amount, purpose);
	}
	public void createTransaction() {
		TransactionView newTransactionView = TransactionView.create();
		this.pendingTransfersAndOrTransactions.addElement(newTransactionView);
	}
	public ListModel<TransferOrTransactionView> getCurrentTransactionDetails() {
		return this.currentTransactionDetails;
	}
	public void book(TransferOrTransactionView transferOrTransaction) throws AccountException, UnderLimitException {
		transferOrTransaction.getTransferOrTransaction().book();
		this.pendingTransfersAndOrTransactions.removeElement(transferOrTransaction);
	}
}
