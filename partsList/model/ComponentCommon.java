package model;


public abstract class ComponentCommon implements Component {

	protected final String name;
	protected static int price;

	protected ComponentCommon(final String name, int price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String toString(){
		return this.getName();
	}
	@Override
	public boolean equals(Object argument) {
		return super.equals(argument);
	}
	
	@Override
	public int getPrice() {
		return this.price;
	}
}
