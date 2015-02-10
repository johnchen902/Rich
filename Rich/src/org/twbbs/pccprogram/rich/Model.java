package org.twbbs.pccprogram.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Model {

	private List<Player> players = new ArrayList<>();
	private List<Place> places = new ArrayList<>();
	private int turnTo;
	private boolean gameOver;

	public void initDefault() {
		players.add(new Player("A", 50000));
		players.add(new Player("B", 50000));
		places.add(new Place("Start"));
		addDefaultSide("S");
		places.add(new Place("Corner 1"));
		addDefaultSide("W");
		places.add(new Place("Corner 2"));
		addDefaultSide("N");
		places.add(new Place("Corner 3"));
		for (int i = 1; i <= 4; i++)
			places.add(new Place("EX " + i));
		addDefaultSide("E");
	}

	private void addDefaultSide(String prefix) {
		for (int i = 0; i < 15; i++)
			places.add(new BuyablePlace(prefix + " " + (i + 1), i % 2 == 0 ? 1
					: i % 4 == 1 ? 2 : i / 4 + 3));
	}

	public int getPlayerCount() {
		return players.size();
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public void addPlayer(Player p) {
		players.add(Objects.requireNonNull(p));
	}

	public int getTurnTo() {
		return turnTo;
	}

	public void setTurnTo(int player) {
		if (player < 0 || player >= players.size())
			throw new IllegalArgumentException("no such player");
		this.turnTo = player;
	}

	public void toNextTurn() {
		setTurnTo((getTurnTo() + 1) % players.size());
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Place getPlace(int placeId) {
		return places.get(placeId);
	}

	public int getRent(int lv, int houses) {
		return 100 * lv * (1 << houses);
	}

	public int getBuyPrice(int lv) {
		return 1000 * lv;
	}

	public int getBuildPrice(int lv) {
		return 1000 * lv;
	}
}
