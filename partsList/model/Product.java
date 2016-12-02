package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import command_n_conquer.Buffer;
import command_n_conquer.Buffer.StoppException;
import command_n_conquer.OPCommand;

public class Product extends ComponentCommon {

	int price;

	private static final String CycleMessage = "Zyklen sind in der Aufbaustruktur nicht erlaubt!";

	public static Product create(final String name, int price) {
		Product p = new Product(name, price, new HashMap<String, QuantifiedComponent>());
		p.changePrice(price);
		return p;
	}

	private final HashMap<String, QuantifiedComponent> components;

	protected Product(final String name, int price, final HashMap<String, QuantifiedComponent> components) {
		super(name, price);
		this.components = components;
	}

	@Override
	public void addPart(final Component part, final int amount) throws Exception {
		if (part.contains(this))
			throw new Exception(CycleMessage);
		final String partName = part.getName();
		if (this.getComponents().containsKey(partName)) {
			final QuantifiedComponent oldQuantification = this.getComponents().get(partName);
			oldQuantification.addQuantity(amount);
		} else {
			this.getComponents().put(partName, QuantifiedComponent.createQuantifiedComponent(amount, part));
		}
	}

	private HashMap<String, QuantifiedComponent> getComponents() {
		return this.components;
	}

	@Override
	public boolean contains(final Component component) {
		if (this.equals(component))
			return true;
		final Iterator<QuantifiedComponent> i = this.getComponents().values().iterator();
		while (i.hasNext()) {
			final QuantifiedComponent current = i.next();
			if (current.contains(component))
				return true;
		}
		return false;
	}

	@Override
	public Vector<QuantifiedComponent> getDirectParts() {
		return new Vector<QuantifiedComponent>(this.getComponents().values());
	}

	@Override
	public int getNumberOfMaterials() {
		int result = 0;
		final Iterator<QuantifiedComponent> i = this.getComponents().values().iterator();
		while (i.hasNext()) {
			final QuantifiedComponent current = i.next();
			result = result + current.getNumberOfMaterials();
		}
		return result;
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
		int overallPrice = this.getPrice();
		Iterator<QuantifiedComponent> i = this.components.values().iterator();
		while (i.hasNext()) {
			QuantifiedComponent current = i.next();
			overallPrice = current.getOverallPrice() + overallPrice;
		}
		return overallPrice;
	}

	public int getOverallPriceParallel() {
		int result = price, counter = 0;
		Buffer<OPCommand> calls = new Buffer<OPCommand>(10);
		Iterator<QuantifiedComponent> i = this.getComponents().values().iterator();
		while (i.hasNext()) {
			QuantifiedComponent current = i.next();
			OPCommand command = new OPCommand(current, calls);
			counter++;
			new Thread(new Runnable() {

				@Override
				public void run() {
					ThreadCounter.number++;
					command.execute();
				}
			}, "Thread-" + current.getComponent().getName()).start();
		}
		for (int j = 0; j < counter; j++) {
			try {
				result = result + calls.get().getResult();
			} catch (StoppException e) {
			}
		}
		System.out.println("Materials and Prices of \""+this.name +"\" = "+result);
		return result;
	}

	@Override
	public Vector<QuantifiedComponent> getMaterialList() {
		return new Vector<QuantifiedComponent>();
	}

}
