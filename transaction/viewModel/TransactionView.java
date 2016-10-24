package viewModel;

import model.Transaction;
import model.TransferOrTransaction;

public class TransactionView implements TransferOrTransactionView {

	private static final String TransactionPrefix = "TRANSACTION ";
	
	private static int nextTransactionNumber = 1;
	
	public static TransactionView create() {
		return new TransactionView(nextTransactionNumber++);
	}
	private int number;

	private Transaction transaction;

	private SpecialDefaultListModel<TransferOrTransactionView> transfers;
	
	public TransactionView(int number) {
		this.number = number;
		this.transaction = Transaction.create();
		this.transfers = new SpecialDefaultListModel<TransferOrTransactionView>();
	}
	public String toString(){
		return TransactionPrefix + this.number;
	}
	Transaction getTransaction() {
		return transaction;
	}
	@Override
	public void accept(TransferOrTransactionViewVisitor visitor) {
		visitor.handleTransactionView(this);
	}
	public void addTransfer(AccountView from, AccountView to, long amount, String purpose) {
		TransferView newTransfer = TransferView.create(from, to, amount, purpose);
		this.transaction.addTransfer(newTransfer.getTransfer());
		this.transfers.addElement(newTransfer);
	}
	public SpecialDefaultListModel<TransferOrTransactionView> getDetails(){
		return this.transfers;
	}
	@Override
	public TransferOrTransaction getTransferOrTransaction() {
		return this.transaction;
	}
}
