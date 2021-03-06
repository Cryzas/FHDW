package model;

public class QuantifiedComponent {
	
	private static final String QuantityOpenBracket = "(";
	private static final String QuantityCloseBracket = ")";

	public static QuantifiedComponent createQuantifiedComponent (final int quantity, final Component component){
		return new QuantifiedComponent(quantity,component);
	}
	private int quantity;
	final private Component component;
	
	private QuantifiedComponent(final int quantity, final Component component){
		this.quantity = quantity;
		this.component = component;
	}

	public Component getComponent() {
		return this.component;
	}

	public int getQuantity() {
		return this.quantity;
	}
	private void setQuantity(final int quantity){
		this.quantity = quantity;
	}
	public void addQuantity(final int quantity) {
		this.setQuantity(this.getQuantity() + quantity);
	}
	@Override
	public boolean equals(Object argument) {
		return super.equals(argument);
	}
	public boolean contains(final Component part) {
		return this.getComponent().contains(part);
	}
	@Override
	public String toString() {
		return this.getComponent().toString() + QuantityOpenBracket + this.getQuantity() + QuantityCloseBracket;
	}

	public int getNumberOfMaterials() {
		return this.getComponent().getNumberOfMaterials() * this.getQuantity();
	}

	public int getOverallPrice() {
		return this.getComponent().getOverallPrice()* this.getQuantity();
	}

	public int getOverallPriceParallel() {
		return this.getComponent().getOverallPriceParallel() * this.quantity;
	}
}
