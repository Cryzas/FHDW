package view;


import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.AccountException;
import model.UnderLimitException;
import viewModel.ViewModel;
import view.View;
import viewModel.AccountTransactionFacadeView;
import viewModel.AccountView;
import viewModel.EntryView;
import viewModel.TransactionView;
import viewModel.TransferOrTransactionView;
import viewModel.TransferOrTransactionViewVisitor;
import viewModel.TransferView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@SuppressWarnings("serial")
public class View extends JFrame implements AccountTransactionFacadeView {

	private static final int ViewWidth = 800;
	private static final int ViewHeight = 600;
	public View(int offset, String title) {
		super();
		initialize(offset, title);
	}

	private void initialize(int offset, String title) {
		this.setBounds(offset, offset, offset + ViewWidth, offset + ViewHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		this.getContentPane().setLayout(new BorderLayout(0, 0));		
		this.getContentPane().add(getMainSplitPane(), BorderLayout.CENTER);
		this.getContentPane().add(this.getMainStatusBar(),BorderLayout.SOUTH);
		this.getContentPane().addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				adJustDividerLocations();
			}
			public void componentMoved(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});
		
	}
	JSplitPane mainSplitPane = null;
	private Component getMainSplitPane() {
		if (this.mainSplitPane == null) {
			this.mainSplitPane = new JSplitPane();
			this.mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			mainSplitPane.setLeftComponent(getAccountsPanel());
			mainSplitPane.setRightComponent(getOtherAccountsAndTransactionsPanel());
			adJustDividerLocations();
		}
		return this.mainSplitPane;
	}
	private JPanel accountDetailsPanel = null;
	private JPanel getAccountDetailsPanel(){
		if (this.accountDetailsPanel == null) {
			this.accountDetailsPanel = new JPanel();
			this.accountDetailsPanel.setLayout(new BorderLayout());
			this.accountDetailsPanel.setBorder(new TitledBorder("Account details"));
			this.accountDetailsPanel.add(getAccountEntryListScrollPane(), BorderLayout.CENTER);
		}
		return this.accountDetailsPanel;
	}
	private JScrollPane accountEntryListScrollPane = null;
	private JScrollPane getAccountEntryListScrollPane(){
		if (this.accountEntryListScrollPane == null) {
			this.accountEntryListScrollPane = new JScrollPane();
			this.accountEntryListScrollPane.setViewportView(getAccountEntryList());
		}
		return this.accountEntryListScrollPane;
	}
	private JList<EntryView> accountEntryList = null;
	private JList<EntryView> getAccountEntryList(){
		if (this.accountEntryList == null) {
			this.accountEntryList = new JList<EntryView>();
			this.accountEntryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.accountEntryList.setModel(this.viewModel.getCurrentAccountEntries());
			this.accountEntryList.setToolTipText("Shows the entries of the selected user account.");
		}
		return this.accountEntryList;
	}
	private void adJustDividerLocations() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainSplitPane.setDividerLocation((double) 0.4);
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						accountsSplitPane.setDividerLocation((double) 0.2);
						transactionsSplitPane.setDividerLocation((double) 0.2);
					}
				});
			}
		});			
	}
	private JPanel accountsPanel = null;
	private JPanel getAccountsPanel() {
		if (this.accountsPanel == null) {
			this.accountsPanel = new JPanel();
			this.accountsPanel.setLayout(new BorderLayout());
			this.accountsPanel.add(this.getAccountsToolBar(),BorderLayout.NORTH);
			this.accountsPanel.add(this.getAccountsSplitPane(),BorderLayout.CENTER);
		}
		return this.accountsPanel;
	}
	private JSplitPane accountsSplitPane = null;
	private JSplitPane getAccountsSplitPane(){
		if (this.accountsSplitPane == null) {
			this.accountsSplitPane = new JSplitPane();
			this.accountsSplitPane.setBorder(new TitledBorder("My Accounts"));
			this.accountsSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			this.accountsSplitPane.add(this.getMyAccountsPanel(),JSplitPane.LEFT);
			this.accountsSplitPane.add(this.getAccountDetailsPanel(),JSplitPane.RIGHT);
			this.adJustDividerLocations();
		}
		return this.accountsSplitPane;
	}
	private JPanel myAccountsPanel = null;
	private JPanel getMyAccountsPanel() {
		if (this.myAccountsPanel == null) {
			this.myAccountsPanel = new JPanel();
			this.myAccountsPanel.setLayout(new BorderLayout());
			this.myAccountsPanel.add(this.getMyAccountListScrollBar());
		}
		return this.myAccountsPanel;
	}
	private JScrollPane myAccountListScrollBar = null;
	private JScrollPane getMyAccountListScrollBar (){
		if (this.myAccountListScrollBar == null) {
			this.myAccountListScrollBar = new JScrollPane();
			this.myAccountListScrollBar.setViewportView(this.getMyAccountList());
		}
		return this.myAccountListScrollBar;
	}
	private JList<AccountView> myAccountList = null;
	private JList<AccountView> getMyAccountList(){
		if (this.myAccountList == null) {
			this.myAccountList = new JList<AccountView>();
			this.myAccountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.myAccountList.setModel(this.getViewModel().getMyAccountList());
			this.myAccountList.setToolTipText("Shows the user's accounts.");
			this.myAccountList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					myAccountListSelectionChangedAction();
				}
			});
		}
		return this.myAccountList;
	}
	private JPanel otherAccountsPanel = null;
	private JPanel getOtherAccountsPanel() {
		if (this.otherAccountsPanel == null) {
			this.otherAccountsPanel = new JPanel();
			this.otherAccountsPanel.setBorder(new TitledBorder("Other Accounts"));
			this.otherAccountsPanel.setLayout(new BorderLayout());
//			this.otherAccountsPanel.add(getOtherAccountsToolBar(),BorderLayout.NORTH);
			this.otherAccountsPanel.add(this.getOtherAccountsViewToolBar(),BorderLayout.SOUTH);
			this.otherAccountsPanel.add(this.getOtherAccountListScrollBar(), BorderLayout.CENTER);
		}
		return this.otherAccountsPanel;
	}
	private JScrollPane otherAccountListScrollBar = null;
	private JScrollPane getOtherAccountListScrollBar (){
		if (this.otherAccountListScrollBar == null) {
			this.otherAccountListScrollBar = new JScrollPane();
			this.otherAccountListScrollBar.setViewportView(this.getOtherAccountList());
		}
		return this.otherAccountListScrollBar;
	}
	private JList<AccountView> otherAccountList = null;
	private JList<AccountView> getOtherAccountList(){
		if (this.otherAccountList == null) {
			this.otherAccountList = new JList<AccountView>();
			this.otherAccountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.otherAccountList.setModel(this.getViewModel().getOtherAccountList());
			this.otherAccountList.setToolTipText("Shows the accounts (own and/or others) that have been found by searches."); 
		}
		return this.otherAccountList;
	}
//	private JToolBar otherAccountsToolBar = null;
//	private JToolBar getOtherAccountsToolBar(){
//		if (this.otherAccountsToolBar == null) {
//			this.otherAccountsToolBar = new JToolBar();
//		}
//		return this.otherAccountsToolBar;
//	}
	private JToolBar otherAccountsViewToolBar = null;
	private JToolBar getOtherAccountsViewToolBar(){
		if (this.otherAccountsViewToolBar == null) {
			this.otherAccountsViewToolBar = new JToolBar();
			this.otherAccountsViewToolBar.add(this.getClearAccountViewButton());
		}
		return this.otherAccountsViewToolBar;
	}
	private JButton clearAccountViewButton = null;
	private JButton getClearAccountViewButton(){
		if (this.clearAccountViewButton == null) {
			this.clearAccountViewButton = new JButton("Clear");
			this.clearAccountViewButton.setToolTipText("Clears the list of found accounts.");
			this.clearAccountViewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clearAccountViewAction();
				}
			});
		}
		return this.clearAccountViewButton;
	}
	private JToolBar accountsToolBar = null;
	private JToolBar getAccountsToolBar(){
		if (this.accountsToolBar == null) {
			this.accountsToolBar = new JToolBar();
			this.accountsToolBar.add(new JLabel("Account name: "));
			this.accountsToolBar.add(this.getAccountNameInput());
			this.accountsToolBar.add(this.getCreateAccountButton());
			this.accountsToolBar.add(this.getFindAccountButton());
		}
		return this.accountsToolBar;
	}
	private JButton findAccountButton = null;
	private JButton getFindAccountButton(){
		if (this.findAccountButton == null) {
			this.findAccountButton = new JButton("Find account");
			this.findAccountButton.setToolTipText("Searches for an acount with name \"Account name\" and lists it under \"Other accounts\", if found.");
			this.findAccountButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					findAccountAction();
				}
			});
		}
		return this.findAccountButton;
	}
	private JButton createAccountButton = null;
	private JButton getCreateAccountButton(){
		if (this.createAccountButton == null) {
			this.createAccountButton = new JButton("Create account");
			this.createAccountButton.setToolTipText("Creates an acount with name \"Account name\" and lists it under \"My accounts\".");
			this.createAccountButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					createAccountAction();
					getAccountNameInput().grabFocus();
				}
			});
		}
		return this.createAccountButton;
	}
	private JTextField accountNameInput = null;
	JTextField getAccountNameInput(){
		if (this.accountNameInput == null) {
			this.accountNameInput = new JTextField();
			this.accountNameInput.setText("Konto");
		}
		return this.accountNameInput;
	}
	private JPanel otherAccountsAndTransactionsPanel = null;
	private JPanel getOtherAccountsAndTransactionsPanel() {
		if (this.otherAccountsAndTransactionsPanel == null) {
			this.otherAccountsAndTransactionsPanel = new JPanel();
			this.otherAccountsAndTransactionsPanel.setLayout(new BorderLayout());
			this.otherAccountsAndTransactionsPanel.add(this.getTransactionsSplitPane(), BorderLayout.CENTER);
		}
		return this.otherAccountsAndTransactionsPanel;
	}
	private JSplitPane transactionsSplitPane = null;
	private JSplitPane getTransactionsSplitPane(){
		if (this.transactionsSplitPane == null) {
			this.transactionsSplitPane = new JSplitPane();
			this.transactionsSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			this.transactionsSplitPane.add(getOtherAccountsPanel(),JSplitPane.LEFT);
			this.transactionsSplitPane.add(getTransactionsAndTransfersPanel(),JSplitPane.RIGHT);
			this.adJustDividerLocations();
		}
		return this.transactionsSplitPane;
	}
	private JPanel transactionsAndTransfersPanel = null;
	private JPanel getTransactionsAndTransfersPanel() {
		if (transactionsAndTransfersPanel == null) {
			this.transactionsAndTransfersPanel = new JPanel();
			this.transactionsAndTransfersPanel.setLayout(new BorderLayout());
			this.transactionsAndTransfersPanel.setBorder(new TitledBorder("Transfers and transactions"));
			this.transactionsAndTransfersPanel.add(this.getTransactionsAndTransfersSplitPane(), BorderLayout.CENTER);
			this.transactionsAndTransfersPanel.add(getTransferToolBar(), BorderLayout.NORTH);
			this.transactionsAndTransfersPanel.add(this.getBookToolBar(), BorderLayout.SOUTH);
		}
		return this.transactionsAndTransfersPanel;
	}
	private JToolBar bookToolBar = null;
	private JToolBar getBookToolBar() {
		if (this.bookToolBar == null) {
			this.bookToolBar = new JToolBar();
			this.bookToolBar.add(this.getBookButton());
		}
		return this.bookToolBar;
	}
	private JButton bookButton = null;
	private JButton getBookButton() {
		if (this.bookButton == null) {
			this.bookButton = new JButton("Book!");
			this.bookButton.setToolTipText("Books the selected transfer or transaction.");
			this.bookButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					bookAction();
				}
			});
		}
		return this.bookButton;
	}
	private JSplitPane transactionsAndTransfersSplitPane = null;
	private JSplitPane getTransactionsAndTransfersSplitPane() {
		if (this.transactionsAndTransfersSplitPane == null) {
			this.transactionsAndTransfersSplitPane = new JSplitPane();
			this.transactionsAndTransfersSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			this.transactionsAndTransfersSplitPane.add(this.getTransactionsPanel(),JSplitPane.LEFT);
			this.transactionsAndTransfersSplitPane.add(this.getTransactionDetailsPanel(),JSplitPane.RIGHT);
		}
		return this.transactionsAndTransfersSplitPane;
	}
	private JPanel transactionDetailsPanel = null;
	private JPanel getTransactionDetailsPanel() {
		if (this.transactionDetailsPanel == null) {
			this.transactionDetailsPanel = new JPanel();
			this.transactionDetailsPanel.setLayout(new BorderLayout());
			this.transactionDetailsPanel.setBorder(new TitledBorder("Transaction details"));
			this.transactionDetailsPanel.add(getTransactionDetailsScrollPane(), BorderLayout.CENTER);
			this.hideTransactionDetails();
		}
		return this.transactionDetailsPanel;
	}
	private JScrollPane transactionDetailsScrollPane = null;
	private JScrollPane getTransactionDetailsScrollPane() {
		if (this.transactionDetailsScrollPane == null) {
			this.transactionDetailsScrollPane = new JScrollPane();
			this.transactionDetailsScrollPane.setViewportView(getTransactionDetailList());
		}
		return this.transactionDetailsScrollPane;
	}
	private JList<TransferOrTransactionView> transactionDetailList = null;
	private JList<TransferOrTransactionView> getTransactionDetailList() {
		if (this.transactionDetailList == null) {
			this.transactionDetailList = new JList<TransferOrTransactionView>();
			this.transactionDetailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.transactionDetailList.setModel(this.getViewModel().getCurrentTransactionDetails());
		}
		return this.transactionDetailList;
	}
	private JPanel transactionsPanel = null;
	private JPanel getTransactionsPanel(){
		if (this.transactionsPanel == null){
			this.transactionsPanel = new JPanel();
			this.transactionsPanel.setLayout(new BorderLayout());
			this.transactionsPanel.add(getTransactionsScrollPane(), BorderLayout.CENTER);
		}
		return this.transactionsPanel;
	}
	private JScrollPane transactionsScrollPane = null;
	private JScrollPane getTransactionsScrollPane(){
		if (this.transactionsScrollPane == null) {
			this.transactionsScrollPane = new JScrollPane();
			this.transactionsScrollPane.setViewportView(getTransferOrTransactionList());
		}
		return this.transactionsScrollPane;
	}
	private JList<TransferOrTransactionView> transferOrTransactionsList = null;
	private JList<TransferOrTransactionView> getTransferOrTransactionList(){
		if (this.transferOrTransactionsList == null) {
			this.transferOrTransactionsList = new JList<TransferOrTransactionView>();
			this.transferOrTransactionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.transferOrTransactionsList.setModel(this.getViewModel().getPendingTransfersAndorTransactions());
			this.transferOrTransactionsList.setToolTipText("Shows the created and pending transfers and/or transactions.");
			this.transferOrTransactionsList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					transferOrTransactionListSelectionChangedAction();
				}
			});
		}
		return this.transferOrTransactionsList;
	}
	private JToolBar transferToolBar = null;
	private JToolBar getTransferToolBar(){
		if (this.transferToolBar == null) {
			this.transferToolBar = new JToolBar();
			this.transferToolBar.add(new JLabel("Amount(€): "));
			this.transferToolBar.add(getAmountInput());
			this.transferToolBar.add(new JLabel(" Purpose: "));
			this.transferToolBar.add(getPurposeInput());
			this.transferToolBar.add(getDebitCreateButton());
			this.transferToolBar.add(getCreditCreateButton());
			this.transferToolBar.add(getCreateTransactionButton());
		}
		return this.transferToolBar;
	}
	private JButton createTransactionButton = null;
	private JButton getCreateTransactionButton() {
		if (this.createTransactionButton == null) {
			this.createTransactionButton = new JButton("Create transaction");
			this.createTransactionButton.setToolTipText("Creates a new empty transaction.");
			this.createTransactionButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					createTransactionAction();
				}
			});
		}
		return this.createTransactionButton;
	}
	private JTextField purposeInput = null;
	private JTextField getPurposeInput(){
		if (this.purposeInput == null) {
			this.purposeInput = new JTextField();
			this.purposeInput.setText("Zweck");
		}
		return this.purposeInput;
	}
	private JTextField amountInput = null;
	private JTextField getAmountInput(){
		if (this.amountInput == null) {
			this.amountInput = new JTextField();
			this.amountInput.setText("1");
		}
		return this.amountInput;
	}
	private JButton debitCreateButton = null;
	private JButton getDebitCreateButton(){
		if (this.debitCreateButton == null) {
			this.debitCreateButton = new JButton("Create debit");
			this.debitCreateButton.setToolTipText("Creates a new transfer (if a transaction is selectedwithin the selected transaction).");
			this.debitCreateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					debitOrCreditCreateAction(Debit);
				}
			});
		}
		return this.debitCreateButton;
	}
	private JButton creditCreateButton = null;
	private JButton getCreditCreateButton(){
		if (this.creditCreateButton == null) {
			this.creditCreateButton = new JButton("Create crebit");
			this.creditCreateButton.setToolTipText("Creates a new transfer (if a transaction is selectedwithin the selected transaction).");
			this.creditCreateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					debitOrCreditCreateAction(Credit);
				}
			});
		}
		return this.creditCreateButton;
	}
	private JLabel mainStatusBar = null;
	
	private JLabel getMainStatusBar() {
		if (this.mainStatusBar == null) {
			this.mainStatusBar = new JLabel();
			this.mainStatusBar.setForeground(Color.RED);
		}
		return this.mainStatusBar;
	}
	private static final long StandardErrorShowTime = 10000;
	private static final long StandardErrorUpdateInterval = 1000;
	private long errorShowTime = 0;
	private synchronized void increaseErrorShowTime(String message) {
		this.getMainStatusBar().setText(message);
		this.errorShowTime = new java.util.Date().getTime() + StandardErrorShowTime;
		this.getMainStatusBar().setVisible(true);
		if (this.errorResetter == null){
			this.errorResetter = this.getErrorResetter();
			this.errorResetter.start();
		}
	}
	private Thread errorResetter;  
	private Thread getErrorResetter() {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						synchronized (View.this) {
							View.this.wait(View.StandardErrorUpdateInterval);
						}
						if (new java.util.Date(errorShowTime).before(new java.util.Date())) stopErrorShow();
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});
	}

	private synchronized void stopErrorShow(){
		this.getMainStatusBar().setVisible(false);
	}

	private void showError(String message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				increaseErrorShowTime(message);
			}
		}). start();
	}
	protected void showTransactionDetails(TransactionView transactionView) {
		this.getTransactionDetailsPanel().setVisible(true);
		this.getTransactionsAndTransfersSplitPane().setDividerLocation((double) 0.6);
		this.getTransactionsAndTransfersSplitPane().setDividerSize(10);
		this.getTransactionDetailList().setModel(this.getViewModel().getCurrentTransactionDetails());
	}
	protected void hideTransactionDetails() {
		this.getTransactionDetailsPanel().setVisible(false);
		this.getTransactionsAndTransfersSplitPane().setDividerSize(0);
	}

	/* End of graphical user interface ***********************************************************************/

	private ViewModel viewModel;
	private ViewModel getViewModel() {
		if (this.viewModel == null) {
			this.viewModel = ViewModel.create(this);
		}
		return this.viewModel;
	}

	protected void createAccountAction() {
		try {
			this.getViewModel().createAccount(this.getAccountNameInput().getText());
			this.getMyAccountList().setSelectedIndex(this.getMyAccountList().getModel().getSize() - 1);
		} catch (AccountException e) {
			this.showError(e.getMessage());
		}
	}
	protected void findAccountAction() {
		try {
			this.getViewModel().findAccount(this.getAccountNameInput().getText());
			this.getOtherAccountList().setSelectedIndex(this.getOtherAccountList().getModel().getSize() - 1);
		} catch (AccountException e) {
			this.showError(e.getMessage());
		}
	}
	protected void clearAccountViewAction() {
		this.getViewModel().clearOtherAccounts();
	}
	protected void myAccountListSelectionChangedAction() {
		AccountView selected = this.getMyAccountList().getSelectedValue();
		if (selected != null) {
			this.getViewModel().changeAccountSelection(selected);
		}
	}
	public void updateEntriesOfSelectedAccount(){
		this.getAccountEntryList().setModel(this.getViewModel().getCurrentAccountEntries());
	}
	private static final int Credit = 0;
	private static final int Debit = 1;
	
	protected void debitOrCreditCreateAction(int creditOrDebit) {
		AccountView myAccount = this.getMyAccountList().getSelectedValue();
		if (myAccount == null) {
			this.showError("Select an account in the your own account list!");
			return;
		}
		AccountView otherAccount = this.getOtherAccountList().getSelectedValue();
		if (otherAccount == null) {
			this.showError("Select an account in the list of other accounts!");
			return;
		}
		long amount; 
		try {
			amount = Long.parseLong(this.getAmountInput().getText());
		} catch (NumberFormatException nfe) {
			this.showError("The amount shall be an integer!");
			return;
		}
		AccountView fromAccount;
		AccountView toAccount;
		if (creditOrDebit == Credit) {
			fromAccount = otherAccount;
			toAccount = myAccount;
		} else {
			fromAccount = myAccount;
			toAccount = otherAccount;
		}
		String purpose = this.getPurposeInput().getText();
		TransferOrTransactionView selectedTransferOrTransaction = this.getTransferOrTransactionList().getSelectedValue();
		if (selectedTransferOrTransaction != null) {
			selectedTransferOrTransaction.accept(new TransferOrTransactionViewVisitor() {
				@Override
				public void handleTransferView(TransferView transferView) {
					getViewModel().createTransfer(fromAccount, toAccount, amount, purpose);
				}
				@Override
				public void handleTransactionView(TransactionView transactionView) {
					getViewModel().createTransferInTransaction(fromAccount, toAccount, amount, purpose, transactionView);
					transferOrTransactionListSelectionChangedAction();
				}
			});
		} else {
			this.getViewModel().createTransfer(fromAccount, toAccount, amount, purpose);
		}
	}
	
	protected void createTransactionAction() {
		this.getViewModel().createTransaction();
	}
	protected void transferOrTransactionListSelectionChangedAction() {
		TransferOrTransactionView selected = this.getTransferOrTransactionList().getSelectedValue();
		if (selected != null) {
			selected.accept(new TransferOrTransactionViewVisitor(){
				@Override
				public void handleTransactionView(TransactionView transactionView) {
					getViewModel().changeTransactionSelection(transactionView);
					showTransactionDetails(transactionView);
				}
				@Override
				public void handleTransferView(TransferView transferView) {
					hideTransactionDetails();
				}
			});
		} else {
			if (getTransferOrTransactionList().getModel().getSize() > 0) {
				getTransferOrTransactionList().setSelectedIndex(0);
			} else {
				hideTransactionDetails();
			}
		}
	}
	
	protected void bookAction() {
		TransferOrTransactionView selected = this.getTransferOrTransactionList().getSelectedValue();
		if (selected == null) {
			this.showError("Select a transfer or transaction prior to booking!");
			return;
		}
		try {
			this.getViewModel().book(selected);
		} catch (AccountException | UnderLimitException e) {
			showError(e.getMessage());;
		}
	}

}
