package org.twbbs.pccprogram.rich;

import java.util.Objects;

/**
 * The data of a player.
 * 
 * @author johnchen902
 */
public class Player {

	private String id;
	private int location;
	private int gold;
	private boolean bankrupt;

	/**
	 * Create a player with a specific id and gold.
	 * 
	 * @param id
	 *            the player's id
	 * @param gold
	 *            the player's gold
	 */
	public Player(String id, int gold) {
		setId(id);
		setGold(gold);
	}

	/**
	 * Get the location of this player.
	 * 
	 * @return the location
	 * @see #setLocation(int)
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * Set the location of this player.
	 * 
	 * @param location
	 *            the location
	 * @throws IllegalArgumentException
	 *             if the location is out of valid range
	 * @see #getLocation()
	 */
	public void setLocation(int location) throws IllegalArgumentException {
		if (location < 0 || location >= 68)
			throw new IllegalArgumentException("no such location");
		this.location = location;
	}

	/**
	 * Move this player forward. May overflow if {@code movement} is far too
	 * large.
	 * 
	 * @param movement
	 *            the number of steps to move
	 * @throws IllegalArgumentException
	 *             if movement is negative
	 * @see #setLocation(int)
	 */
	public void move(int movement) throws IllegalArgumentException {
		if (movement < 0)
			throw new IllegalArgumentException("Cannot move backward");
		setLocation((getLocation() + movement) % 68);
	}

	/**
	 * Get the id of this player.
	 * 
	 * @return the id
	 * @see #setId(String)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of this player.
	 * 
	 * @param id
	 *            the id
	 * @see #getId()
	 */
	public void setId(String id) {
		this.id = Objects.requireNonNull(id);
	}

	/**
	 * Get the amount of gold owned by this player.
	 * 
	 * @return the amount of gold
	 * @see #setGold(int)
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * Set the amount of gold owned by this player.
	 * 
	 * @param gold
	 *            the amount of gold
	 * @throws IllegalArgumentException
	 *             if the amount of gold is negative
	 * @see #getGold()
	 */
	public void setGold(int gold) throws IllegalArgumentException {
		if (gold < 0)
			throw new IllegalArgumentException("negative gold");
		this.gold = gold;
	}

	/**
	 * Determines if this player has been bankrupted.
	 * 
	 * @return {@code true} if the player has been bankrupted, {@code false}
	 *         otherwise
	 * @see #setBankrupt(boolean)
	 */
	public boolean isBankrupt() {
		return bankrupt;
	}

	/**
	 * Set if this player has been bankrupted.
	 * 
	 * @param bankrupt
	 *            {@code true} if the player has been bankrupted, {@code false}
	 *            otherwise
	 * @see #isBankrupt()
	 */
	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
}
