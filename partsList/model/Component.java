package model;

import java.util.Vector;

public interface Component {

	/**
	 * Adds amount pieces of the component part as subparts of the receiver. 
	 * @throws Exception If adding part as subpart of whole violates the hierarchy contraint of the partslist represented by the receiver.
	 */
	public void addPart(Component part, int amount) throws Exception;
	/**
	 * Returns true if and only if the receiver directly or indirectly contains the given component.
	 */
	public boolean contains(Component component);
	/**
	 * Returns the list of all quantified parts that are direct sub-parts of the receiver.
	 */
	public Vector<QuantifiedComponent> getDirectParts();
	/**
	 * Returns the total number of all materials that are directly or indirectly parts of the receiver.
	 */
	public int getNumberOfMaterials();
	
	public String getName();
	
	public void changePrice(int newPrice);
	
	public int getPrice();
	
	public int getOverallPrice();
	
	public Vector<QuantifiedComponent> getMaterialList();
	public int getOverallPriceParallel();
	
}
