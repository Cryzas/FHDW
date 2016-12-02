package model;
import java.util.Vector;


public class Material extends ComponentCommon {

	private static final String UnstructuredMaterialMessage = "Materialien haben kein Struktur!";
	public static Material create(final String name, int price) {
		Material m = new Material(name,price);
		m.changePrice(price);
		return m;
	}
	public Material(final String name, int price) {
		super(name, price);
		this.price = price;
	}
	@Override
	public void addPart(final Component part, final int amount) throws Exception {
		throw new Exception(UnstructuredMaterialMessage);
	}
	@Override
	public boolean contains(final Component component) {
		return this.equals(component);
	}
	@Override
	public Vector<QuantifiedComponent> getDirectParts() {
		return new Vector<QuantifiedComponent>();
	}
	@Override
	public int getNumberOfMaterials() {
		return 1;
	}
	@Override
	public void changePrice(int newPrice) {
		this.price = newPrice;
		
	}
	@Override
	public int getPrice() {
		return this.price;
	}
	@Override
	public int getOverallPrice() {
		return this.getPrice();
	}
	@Override
	public Vector<QuantifiedComponent> getMaterialList() {
		return new Vector<QuantifiedComponent>();
	}
	@Override
	public int getOverallPriceParallel() {
		return this.getPrice();
	}

}
