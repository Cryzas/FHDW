package viewModel;

import model.CreditEntry;
import model.Entry;
import model.EntryVisitor;

public class EntryView {

	protected static final String DebitPrefix = "Debit: ";
	protected static final String CreditPrefix = "Credit: ";

	public static EntryView create(Entry entry) {
		return new EntryView(entry);
	}
	private Entry entry;
	
	public EntryView(Entry entry) {
		this.entry = entry;
	}
	
	public String toString(){
		return this.entry.acceptEntryVisitor(new EntryVisitor<String>(){

			@Override
			public String handleDebitEntry(Entry debitEntry) {
				return DebitPrefix + debitEntry.getAmount() +" Zweck: " +debitEntry.getPurpose();
			}

			@Override
			public String handleCreditEntry(CreditEntry creditEntry) {
				return CreditPrefix + creditEntry.getAmount() +" Zweck: " +creditEntry.getPurpose();
			}
			
		});
	}

}
