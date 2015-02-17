package org.twbbs.pccprogram.rich;

/**
 * The data of a place that can be bought by players.
 * 
 * @author johnchen902
 */
public class BuyablePlace extends Place {

	private Player owner; // null means no owner
	private int level;
	private int houses;

	/**
	 * Create a buy-able place with specific name and level.
	 * 
	 * @param name
	 *            its name
	 * @param level
	 *            its level
	 * @throws IllegalArgumentException
	 *             if the level is not between 1 and 5, inclusive
	 */
	public BuyablePlace(String name, int level) throws IllegalArgumentException {
		super(name);
		if (level < 1 || level > 5)
			throw new IllegalArgumentException("no such level");
		this.level = level;
	}

	/**
	 * Get the number of houses built on this place.
	 * 
	 * @return the number of houses
	 * @see #setHouses(int)
	 */
	public int getHouses() {
		return houses;
	}

	/**
	 * Set the number of houses built on this place.
	 * 
	 * @param houses
	 *            the number of houses
	 * @throws IllegalArgumentException
	 *             if the number of houses is below zero or above five
	 * @see #getHouses()
	 */
	public void setHouses(int houses) throws IllegalArgumentException {
		if (houses < 0)
			throw new IllegalArgumentException("too few houses");
		if (houses > 5)
			throw new IllegalArgumentException("too many houses");
		this.houses = houses;
	}

	/**
	 * Get the level of this place.
	 * 
	 * @return its level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Get the owner of this place.
	 * 
	 * @return its owner, or {@code null} if nobody owns here
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Set the owner of this place.
	 * 
	 * @param owner
	 *            its owner, or {@code null} if nobody owns here
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
