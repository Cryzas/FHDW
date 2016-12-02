package viewPartsList;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import model.Component;
import model.PartsList;
import model.QuantifiedComponent;

public class View extends JFrame {

	final private PartsList manager;

	private static final long serialVersionUID = 1L;

	protected static final String NameCannotBeEmptyText = "Name darf nicht leer sein!"; // @jve:decl-index=0:

	protected static final String AmountMustBeIntegerText = "Anzahl muss ganze Zahl sein!"; // @jve:decl-index=0:

	protected static final String PriceMustBeIntegerText = "Neuer Preis muss ganze Zahl sein!"; // @jve:decl-index=0:

	protected static final String SelectWholeAndPartFirstText = "Bitte Ganzes und Teil auswählen!"; // @jve:decl-index=0:

	protected static final String SelectComponentFirstText = "Bitte Komponente auswählen!"; // @jve:decl-index=0:

	private JSplitPane mainSplitPane = null;

	private JPanel productPanel = null;

	private JPanel choicePanel = null;

	private JSplitPane productSplitPane = null;

	private JPanel productSelectPanel = null;

	private JPanel directPartPanel = null;

	private JToolBar productToolBar = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JButton newMaterialButton = null;

	private JButton newProductButton = null;

	private JScrollPane productScrollPane = null;

	private JList<Component> productList = null;

	private JScrollPane partsScrollPane = null;

	private JList<QuantifiedComponent> partsList = null;

	private JToolBar choiceToolBar = null;

	private JScrollPane choiceScrollPane = null;

	private JList<Component> choiceList = null;

	private JButton addButton = null;

	private JLabel countLabel = null;

	private JTextField countTextField = null;

	private JLabel statusLabel = null;

	private JToolBar partsToolBar = null;

	private JLabel partsCountLabel = null;

	private JTextField partsCountTextField = null;

	public View(final PartsList manager) {
		super();
		this.manager = manager;
		initialize();
		refresh();
	}

	private PartsList getManager() {
		return this.manager;
	}

	private void initialize() {
		this.statusLabel = new JLabel();
		this.statusLabel.setText("");
		this.setSize(900, 500);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(getMainSplitPane(), BorderLayout.CENTER);
		this.add(statusLabel, BorderLayout.SOUTH);
	}

	private JSplitPane getMainSplitPane() {
		if (this.mainSplitPane == null) {
			this.mainSplitPane = new JSplitPane();
			this.mainSplitPane.setDividerLocation(650);
			this.mainSplitPane.setRightComponent(getChoicePanel());
			this.mainSplitPane.setLeftComponent(getProductPanel());
		}
		return this.mainSplitPane;
	}

	private JPanel getProductPanel() {
		if (this.productPanel == null) {
			this.productPanel = new JPanel();
			this.productPanel.setLayout(new BorderLayout());
			this.productPanel.setBorder(BorderFactory.createTitledBorder(null,
																		 "Produkte und Materialien",
																		 TitledBorder.DEFAULT_JUSTIFICATION,
																		 TitledBorder.DEFAULT_POSITION, 
																		 new Font("Dialog", Font.BOLD, 12), 
																		 new Color(51, 51, 51)));
			this.productPanel.add(getProductSplitPane(), BorderLayout.CENTER);
		}
		return this.productPanel;
	}

	private JPanel getChoicePanel() {
		if (this.choicePanel == null) {
			this.choicePanel = new JPanel();
			this.choicePanel.setLayout(new BorderLayout());
			this.choicePanel.setBorder(BorderFactory.createTitledBorder(null,
																		"Teile-Auswahl", 
																		TitledBorder.DEFAULT_JUSTIFICATION,
																		TitledBorder.DEFAULT_POSITION, 
																		new Font("Dialog", Font.BOLD, 12), 
																		new Color(51, 51, 51)));
			this.choicePanel.add(getChoiceToolBar(), BorderLayout.SOUTH);
			this.choicePanel.add(getChoiceScrollPane(), BorderLayout.CENTER);
		}
		return this.choicePanel;
	}

	private JSplitPane getProductSplitPane() {
		if (this.productSplitPane == null) {
			this.productSplitPane = new JSplitPane();
			this.productSplitPane.setDividerLocation(400);
			this.productSplitPane.setRightComponent(getPartPanel());
			this.productSplitPane.setLeftComponent(getProductSelectPanel());
		}
		return this.productSplitPane;
	}

	private JPanel getProductSelectPanel() {
		if (this.productSelectPanel == null) {
			this.productSelectPanel = new JPanel();
			this.productSelectPanel.setLayout(new BorderLayout());
			this.productSelectPanel.add(getProductToolBar(), BorderLayout.NORTH);
			this.productSelectPanel.add(getPriceChangeToolBar(), BorderLayout.SOUTH);
			this.productSelectPanel.add(getProductScrollPane(), BorderLayout.CENTER);
		}
		return this.productSelectPanel;
	}
	private JToolBar priceChangeToolBar = null;
	private JToolBar getPriceChangeToolBar() {
		if (this.priceChangeToolBar == null){
			this.priceChangeToolBar = new JToolBar();
			this.priceChangeToolBar.add(getPriceChangeButton());
			this.priceChangeToolBar.add(getPriceChangeTextField());
		}
		return this.priceChangeToolBar;
	}
	private JTextField priceChangeTextField = null;
	private JTextField getPriceChangeTextField() {
		if (this.priceChangeTextField == null){
			this.priceChangeTextField = new JTextField();
		}
		return this.priceChangeTextField;
	}
	private JButton priceChangeButton = null;
	private JButton getPriceChangeButton() {
		if(this.priceChangeButton == null){
			this.priceChangeButton = new JButton();
			this.priceChangeButton.setText("Ändere Preis: ");
			this.priceChangeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(final ActionEvent e) {
					priceChanged_action();
				}
			});
		}
		return this.priceChangeButton;
	}
	protected void priceChanged_action() {
		final Component selected = (Component) this.getProductList().getSelectedValue();
		if (selected != null){
			try {
				final int newPrice = Integer.parseInt(this.getPriceChangeTextField().getText());
				this.getManager().changePrice(selected,newPrice);
				this.refresh();
				this.statusLabel.setText("");
			}catch(final NumberFormatException nfe){
				this.statusLabel.setText(PriceMustBeIntegerText);
			}
		}else{
			this.statusLabel.setText(SelectComponentFirstText);
		}
	}
	private JPanel partPanel = null;

	private JPanel getPartPanel() {
		if (this.partPanel == null) {
			this.partPanel = new JPanel();
			this.partPanel.setLayout(new BorderLayout());
			this.partPanel.add(this.getPartSplitPane(), BorderLayout.CENTER);
		}
		return partPanel;
	}

	private JSplitPane partSplitPane = null;

	private JSplitPane getPartSplitPane() {
		if (this.partSplitPane == null) {
			this.partSplitPane = new JSplitPane();
			this.partSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			this.partSplitPane.setDividerLocation(150);
			this.partSplitPane.setLeftComponent(this.getDirectPartPanel());
			this.partSplitPane.setRightComponent(this.getMaterialListPanel());
		}
		return this.partSplitPane;
	}

	private JPanel materialListPanel = null;

	private JPanel getMaterialListPanel() {
		if (this.materialListPanel == null) {
			this.materialListPanel = new JPanel();
			this.materialListPanel.setLayout(new BorderLayout());
			this.materialListPanel.setBorder(BorderFactory.createTitledBorder(null, 
																			  "Materialliste", 
																			  TitledBorder.DEFAULT_JUSTIFICATION,
																			  TitledBorder.DEFAULT_POSITION, 
																			  new Font("Dialog", Font.BOLD, 12), 
																			  new Color(51, 51, 51)));
			this.materialListPanel.add(this.getMaterialListScrollPane(), BorderLayout.CENTER);
			this.materialListPanel.add(getPartsToolBar(), BorderLayout.NORTH);
			this.materialListPanel.add(getOverallPriceToolBar(), BorderLayout.SOUTH);
		}
		return this.materialListPanel;
	}
	private JToolBar overallPriceToolBar = null;
	private JToolBar getOverallPriceToolBar() {
		if(this.overallPriceToolBar == null){
			this.overallPriceToolBar = new JToolBar();
			this.overallPriceToolBar.add(new JLabel(" Gesamtpreis: "));
			this.overallPriceToolBar.add(this.getOverallPriceTextField());
		}
		return this.overallPriceToolBar;
	}
	private JTextField overallPriceTextField = null;
	private JTextField getOverallPriceTextField() {
		if (overallPriceTextField == null){
			this.overallPriceTextField = new JTextField();
		}
		return this.overallPriceTextField;
	}

	private JScrollPane materialListScrollPane = null;

	private JScrollPane getMaterialListScrollPane() {
		if (this.materialListScrollPane == null) {
			this.materialListScrollPane = new JScrollPane();
			this.materialListScrollPane.setViewportView(this.getMaterialList());
		}
		return this.materialListScrollPane;
	}

	private JList<QuantifiedComponent> materialList = null;

	private JList<QuantifiedComponent> getMaterialList() {
		if (this.materialList == null) {
			this.materialList = new JList<QuantifiedComponent>();
		}
		return this.materialList;
	}

	private JPanel getDirectPartPanel() {
		if (this.directPartPanel == null) {
			this.directPartPanel = new JPanel();
			this.directPartPanel.setLayout(new BorderLayout());
			this.directPartPanel.setBorder(BorderFactory.createTitledBorder(null,
																			"Teile", 
																			TitledBorder.DEFAULT_JUSTIFICATION,
																			TitledBorder.DEFAULT_POSITION, 
																			new Font("Dialog", Font.BOLD, 12), 
																			new Color(51, 51, 51)));
			this.directPartPanel.add(getPartsScrollPane(), BorderLayout.CENTER);
		}
		return this.directPartPanel;
	}

	private JToolBar getProductToolBar() {
		if (this.productToolBar == null) {
			this.nameLabel = new JLabel();
			this.nameLabel.setText("Name: ");
			this.productToolBar = new JToolBar();
			this.productToolBar.add(nameLabel);
			this.productToolBar.add(getNameTextField());
			this.productToolBar.add(getNewMaterialButton());
			this.productToolBar.add(getNewProductButton());
			this.productToolBar.add(new JLabel(" Preis: "));
			this.productToolBar.add(getPriceTextField());
		}
		return this.productToolBar;
	}

	private JTextField priceTextField = null;

	private JTextField getPriceTextField() {
		if (this.priceTextField == null) {
			this.priceTextField = new JTextField();
			this.priceTextField.setText("1");
			this.priceTextField.addFocusListener(new FocusListener(){
				@Override
				public void focusGained(final FocusEvent e) {
				}
				@Override
				public void focusLost(final FocusEvent e) {
					try {
						Integer.parseInt(getPriceTextField().getText());
					} catch (final NumberFormatException nfe){
						View.this.priceTextField.setText("1");						
					}
				}
				
			});
		}
		return this.priceTextField;
	}

	private JTextField getNameTextField() {
		if (this.nameTextField == null) {
			this.nameTextField = new JTextField();
		}
		return this.nameTextField;
	}

	private JButton getNewMaterialButton() {
		if (this.newMaterialButton == null) {
			this.newMaterialButton = new JButton();
			this.newMaterialButton.setText("Erzeuge Material");
			this.newMaterialButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(final java.awt.event.ActionEvent e) {
							final String name = getNameTextField().getText();
							final int price = Integer.parseInt(getPriceTextField().getText());
							if (!name.equals("")) {
								try {
									getManager().createMaterial(name, price);
									refresh();
								} catch (final Exception ex) {
									getStatus().setText(ex.getMessage());
								}
							} else {
								getStatus().setText(NameCannotBeEmptyText);
							}
						}
					});
		}
		return this.newMaterialButton;
	}

	protected void refresh() {
		final Vector<Component> components = this.getManager().getComponents();
		this.getProductList().setListData(components);
		this.getChoiceList().setListData(components);
		this.getPartsList().setListData(new Vector<QuantifiedComponent>());
		this.getPartsCountTextField().setText("");
		this.getStatus().setText("");
		this.getNameTextField().setText("");
		this.getCountTextField().setText("1");
		this.getNameTextField().grabFocus();
	}

	protected JLabel getStatus() {
		return this.statusLabel;
	}

	private JButton getNewProductButton() {
		if (this.newProductButton == null) {
			this.newProductButton = new JButton();
			this.newProductButton.setText("Erzeuge Produkt");
			this.newProductButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(final java.awt.event.ActionEvent e) {
							final String name = getNameTextField().getText();
							final int price = Integer.parseInt(getPriceTextField().getText());
							if (!name.equals("")) {
								try {
									getManager().createProduct(name,price);
									refresh();
								} catch (final Exception ex) {
									getStatus().setText(ex.getMessage());
								}
							} else {
								getStatus().setText(NameCannotBeEmptyText);
							}
						}
					});
		}
		return this.newProductButton;
	}

	private JScrollPane getProductScrollPane() {
		if (this.productScrollPane == null) {
			this.productScrollPane = new JScrollPane();
			this.productScrollPane.setViewportView(getProductList());
		}
		return this.productScrollPane;
	}

	private JList<Component> getProductList() {
		if (this.productList == null) {
			this.productList = new JList<Component>();
			this.productList.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(final java.awt.event.MouseEvent e) {
					final Component selected = (Component) getProductList()
							.getSelectedValue();
					if (selected != null) {
						try {
							getPartsList().setListData(getManager().getParts(selected));
							getPartsCountTextField().setText(new Integer(getManager().getMaterialCount(selected)).toString());
						} catch (Exception exc) {
							getStatus().setText(exc.getMessage());
						}
						getMaterialList().setListData(getManager().getMaterialList(selected));
						getOverallPriceTextField().setText(getManager().getOverallPrice(selected));
					}
				}
			});
		}
		return this.productList;
	}

	private JScrollPane getPartsScrollPane() {
		if (this.partsScrollPane == null) {
			this.partsScrollPane = new JScrollPane();
			this.partsScrollPane.setViewportView(getPartsList());
		}
		return this.partsScrollPane;
	}

	private JList<QuantifiedComponent> getPartsList() {
		if (this.partsList == null) {
			this.partsList = new JList<QuantifiedComponent>();
		}
		return this.partsList;
	}

	private JToolBar getChoiceToolBar() {
		if (this.choiceToolBar == null) {
			this.countLabel = new JLabel();
			this.countLabel.setText("Anzahl:");
			this.choiceToolBar = new JToolBar();
			this.choiceToolBar.add(countLabel);
			this.choiceToolBar.add(getCountTextField());
			this.choiceToolBar.add(getAddButton());
		}
		return this.choiceToolBar;
	}

	private JScrollPane getChoiceScrollPane() {
		if (this.choiceScrollPane == null) {
			this.choiceScrollPane = new JScrollPane();
			this.choiceScrollPane.setViewportView(getChoiceList());
		}
		return this.choiceScrollPane;
	}

	private JList<Component> getChoiceList() {
		if (this.choiceList == null) {
			this.choiceList = new JList<Component>();
		}
		return this.choiceList;
	}

	private JButton getAddButton() {
		if (this.addButton == null) {
			this.addButton = new JButton();
			this.addButton.setText("Teil hinzufügen");
			this.addButton.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					try {
						final int amount = Integer.parseInt(getCountTextField()
								.getText());
						final Component whole = (Component) getProductList()
								.getSelectedValue();
						final Component part = (Component) getChoiceList()
								.getSelectedValue();
						if (whole != null && part != null) {
							try {
								getManager().addPart(whole, part, amount);
								refresh();
							} catch (final Exception ex) {
								getStatus().setText(ex.getMessage());
							}
						} else {
							getStatus().setText(SelectWholeAndPartFirstText);
						}
					} catch (final NumberFormatException nfe) {
						getStatus().setText(AmountMustBeIntegerText);
					}
				}
			});
		}
		return this.addButton;
	}

	private JTextField getCountTextField() {
		if (this.countTextField == null) {
			this.countTextField = new JTextField();
		}
		return this.countTextField;
	}

	private JToolBar getPartsToolBar() {
		if (this.partsToolBar == null) {
			this.partsCountLabel = new JLabel();
			this.partsCountLabel.setText("Anzahl: ");
			this.partsToolBar = new JToolBar();
			this.partsToolBar.add(partsCountLabel);
			this.partsToolBar.add(getPartsCountTextField());
		}
		return this.partsToolBar;
	}

	private JTextField getPartsCountTextField() {
		if (this.partsCountTextField == null) {
			this.partsCountTextField = new JTextField();
		}
		return this.partsCountTextField;
	}

	public static View create(final PartsList list) {
		return new View(list);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
