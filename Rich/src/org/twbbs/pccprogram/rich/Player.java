package org.twbbs.pccprogram.rich;

import java.util.Objects;

public class Player {

	private String id;
	private int location;
	private int gold;
	private boolean bankrupt;

	public Player(String id, int gold) {
		setId(id);
		setGold(gold);
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		if (location < 0 || location >= 68)
			throw new IllegalArgumentException("no such location");
		this.location = location;
	}

	public void move(int movement) {
		setLocation((getLocation() + movement) % 68);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Objects.requireNonNull(id);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		if (gold < 0)
			throw new IllegalArgumentException("negative gold");
		this.gold = gold;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
}
