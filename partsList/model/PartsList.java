package model;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


/**
 *  Represents a hierarchical partslist as a mapping from unique part names to components.
 */
public class PartsList {
	
	private static final String DoubleDefinitionMessage = "Name bereits vorhanden!";
	private static final String UnknownComponentMessage = "Unbekannte Komponente: ";

	/**
	 * @return An empty partslist.
	 */
	public static PartsList create (){
		return new PartsList(new HashMap<String, Component>());
	}
	final private HashMap<String, Component> componentsMap;
	
	private PartsList(final HashMap<String, Component> componentsMap){
		this.componentsMap = componentsMap;
	}

	@Override
	public boolean equals(Object argument) {
		return super.equals(argument);
	}
	private HashMap<String, Component> getComponentsMap(){
		return this.componentsMap;
	}
	/**
	 * Creates a new material with the given name and price as a component of the receiver.
	 * @throws Exception If the provided name is already used for another component of the receiver.
	 */
	public void createMaterial(final String name, final int price) throws Exception {
		if (this.getComponentsMap().containsKey(name))
			throw new Exception(DoubleDefinitionMessage);
		final Material newMaterial = Material.create(name,price);
		this.getComponentsMap().put(name, newMaterial);
	}
	/**
	 * Creates a new product with the given name and price as a component of the receiver.
	 * @throws Exception If the provided name is already used for another component of the receiver.
	 */
	public void createProduct(final String name, final int price) throws Exception {
		if (this.getComponentsMap().containsKey(name)) throw new Exception(DoubleDefinitionMessage);
		final Product newProduct = Product.create(name,price);
		this.getComponentsMap().put(name, newProduct);
	}
	/**
	 * Adds amount pieces of the component part as subparts of the component whole. 
	 * @throws Exception <ol> <li> If whole or part are not contained in the partslist of the receiver.</li>
	 *                        <li> If adding part as subpart of whole violates the hierarchy contraint of the partslist represented by the receiver.</li>
	 *                   </ol>
	 */
	public void addPart(final Component whole, final Component part, final int amount) throws Exception {
		if (!this.getComponentsMap().containsValue(whole)) throw new Exception(UnknownComponentMessage + whole.getName());
		if (!this.getComponentsMap().containsValue(part)) throw new Exception(UnknownComponentMessage + part.getName());
		whole.addPart(part,amount);
	}
	/**
	 * Returns the number of materials that are directly or indirectly parts of the given component.
	 * @throws Exception If component is not contained in the partslist of the receiver.
	 */
	public int getMaterialCount(final Component component) throws Exception {
		if (!this.getComponentsMap().containsValue(component)) throw new Exception(UnknownComponentMessage + component.getName());
		return component.getNumberOfMaterials();
	}
	public Vector<Component> getComponents() {
		return new Vector<Component>(this.getComponentsMap().values());
	}
	/**
	 * Returns the list of quantified parts that are the direct subparts of component.
	 * @throws Exception If component is not contained in the partslist of the receiver.
	 */
	public Vector<QuantifiedComponent> getParts(final Component component) throws Exception {
		if (!this.getComponentsMap().containsValue(component)) throw new Exception(UnknownComponentMessage + component.getName());
		return component.getDirectParts();
	}
	
	public Vector<QuantifiedComponent> getMaterialList(final Component component) {
		Vector<QuantifiedComponent> materialList = new Vector<QuantifiedComponent>();
		Iterator<Component> componentIterator = this.componentsMap.values().iterator();
		while (componentIterator.hasNext()) {
			Component currentComponent = (Component) componentIterator.next();
			Iterator<QuantifiedComponent> materialListIterator = currentComponent.getMaterialList().iterator();
			while (materialListIterator.hasNext()) {
				QuantifiedComponent currentQuantifiedComponent = (QuantifiedComponent) materialListIterator.next();
				if(materialList.contains(currentQuantifiedComponent)){
					materialList.get(materialList.indexOf(currentQuantifiedComponent)).addQuantity(currentQuantifiedComponent.getQuantity());
				}
				else {
					materialList.add(currentQuantifiedComponent);
				}
			}
		}
		return materialList;
	}
	
	public String getOverallPrice(final Component component) {
		int overallPrice = component.getOverallPrice();
		return Integer.valueOf(overallPrice).toString();
	}

	public void changePrice(final Component component, final int newPrice) {
		component.changePrice(newPrice);
			if(this.componentsMap.containsKey(component.getName())){
				this.componentsMap.get(component.getName()).changePrice(newPrice);
		}
	}
}
