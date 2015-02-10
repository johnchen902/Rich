package org.twbbs.pccprogram.rich;

public class BuyablePlace extends Place {

	private Player owner; // null means no owner
	private int level;
	private int houses;

	public BuyablePlace(String name, int level) {
		super(name);
		if (level < 1 || level > 5)
			throw new IllegalArgumentException("no such level");
		this.level = level;
	}

	public int getHouses() {
		return houses;
	}

	public void setHouses(int houses) {
		if (houses < 0)
			throw new IllegalArgumentException("too few houses");
		if (houses > 5)
			throw new IllegalArgumentException("too many houses");
		this.houses = houses;
	}

	public int getLevel() {
		return level;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
