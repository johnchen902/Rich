package org.twbbs.pccprogram.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The model that contains the data need for this game.
 * 
 * @author johnchen902
 */
public class Model {

	private List<Player> players = new ArrayList<>();
	private List<Place> places = new ArrayList<>();
	private int turnTo;
	private boolean gameOver;

	/**
	 * Constructor. Also properly initialize itself.
	 */
	public Model() {
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

	/**
	 * Get the number of players.
	 * 
	 * @return the number of players
	 */
	public int getPlayerCount() {
		return players.size();
	}

	/**
	 * Get the player of the specific index.
	 * 
	 * @param i
	 *            the index
	 * @return the player
	 */
	public Player getPlayer(int i) {
		return players.get(i);
	}

	/**
	 * Get the list of players.
	 * 
	 * @return the list of players
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * Add a new player.
	 * 
	 * @param p
	 *            the player to add
	 */
	public void addPlayer(Player p) {
		players.add(Objects.requireNonNull(p));
	}

	/**
	 * Get the index of the currently active player.
	 * 
	 * @return the index
	 * @see #setTurnTo(int)
	 */
	public int getTurnTo() {
		return turnTo;
	}

	/**
	 * Set currently active player.
	 * 
	 * @param player
	 *            the player's index
	 * @see #getTurnTo()
	 */
	public void setTurnTo(int player) {
		if (player < 0 || player >= players.size())
			throw new IllegalArgumentException("no such player");
		this.turnTo = player;
	}

	/**
	 * Set currently active player to next player.
	 * 
	 * @see #setTurnTo(int)
	 */
	public void toNextTurn() {
		setTurnTo((getTurnTo() + 1) % players.size());
	}

	/**
	 * Determines if the game is over.
	 * 
	 * @return {@code true} if the game is over; {@code false} otherwise
	 * @see #setGameOver(boolean)
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Set if the game is over.
	 * 
	 * @param gameOver
	 *            {@code true} if the game is over; {@code false} otherwise
	 * @see #isGameOver()
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Get the place of the specific index.
	 * 
	 * @param placeId
	 *            the index
	 * @return the place
	 */
	public Place getPlace(int placeId) {
		return places.get(placeId);
	}

	/**
	 * Get the list of places.
	 * 
	 * @return the list of places
	 */
	public List<Place> getPlaces() {
		return Collections.unmodifiableList(places);
	}

	/**
	 * Get the list of buy-able places.
	 * 
	 * @return the list of buy-able places
	 */
	public List<BuyablePlace> getBuyablePlaces() {
		return places.stream().filter(BuyablePlace.class::isInstance)
				.map(BuyablePlace.class::cast).collect(Collectors.toList());
	}

	/**
	 * Get the fee to pass a place of the specific level and with specific
	 * number of house.
	 * 
	 * @param level
	 *            the level of the place
	 * @param houses
	 *            the number of houses of the place
	 * @return the fee
	 * @throws IllegalArgumentException
	 *             if the level or the number of houses is out of valid range
	 */
	public int getRent(int level, int houses) throws IllegalArgumentException {
		if (level < 1 || level > 5)
			throw new IllegalArgumentException("level must be between 1 and 5");
		if (houses < 0 || houses > 5)
			throw new IllegalArgumentException(
					"number of houses must be between 0 and 5");
		return 100 * level * (1 << houses);
	}

	/**
	 * Get the fee to buy a place of the specific level.
	 * 
	 * @param level
	 *            the level of the place
	 * @return the fee
	 * @throws IllegalArgumentException
	 *             if the level is out of valid range
	 */
	public int getBuyPrice(int level) throws IllegalArgumentException {
		if (level < 1 || level > 5)
			throw new IllegalArgumentException("level must be between 1 and 5");
		return 1000 * level;
	}

	/**
	 * Get the fee to build a house on a place of the specific level.
	 * 
	 * @param level
	 *            the level of the place
	 * @return the fee
	 * @throws IllegalArgumentException
	 *             if the level is out of valid range
	 */
	public int getBuildPrice(int level) throws IllegalArgumentException {
		if (level < 1 || level > 5)
			throw new IllegalArgumentException("level must be between 1 and 5");
		return 1000 * level;
	}
}
