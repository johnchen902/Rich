package org.twbbs.pccprogram.rich;

import java.util.Objects;

/**
 * The controller of this game. All the logics.
 * 
 * @author johnchen902
 */
public class Control {

	private Model model;
	private View view;

	/**
	 * Just constructor.
	 */
	public Control() {
		model = new Model();
	}

	/**
	 * Set the view to use.
	 * 
	 * @param view
	 *            the view
	 * @throws IllegalStateException
	 *             if the view is already set
	 */
	public void setView(View view) throws IllegalStateException {
		if (this.view != null)
			throw new IllegalStateException("view is already set");
		this.view = Objects.requireNonNull(view);
		NewPlayerWidget np = view.getNewPlayerWidget();
		np.setEnabled(true);
		np.addActionListener(e -> addPlayer());
		DiceWidget dice = view.getDiceWidget();
		dice.setState(DiceWidget.State.ROLLABLE);
		dice.addActionListener(e -> turn());
	}

	/**
	 * Get the model used by this game.
	 * 
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Add a new player
	 */
	private void addPlayer() {
		String id = Character.toString((char) ('A' + model.getPlayerCount()));
		model.addPlayer(new Player(id, 50000));
		if (model.getPlayerCount() == 8)
			view.getNewPlayerWidget().setEnabled(false);
		view.update();
	}

	/**
	 * A single turn of the game.
	 */
	private void turn() {
		// It won't be able to add new player after the start of first turn.
		NewPlayerWidget np = view.getNewPlayerWidget();
		if (np.isEnabled())
			np.setEnabled(false);

		DiceWidget d = view.getDiceWidget();
		Player p = model.getPlayer(model.getTurnTo());

		d.startRolling();
		sleep(100);

		for (int i = 0; i < 10; i++) {
			d.spin();
			sleep(100);
		}

		d.stopRolling();
		sleep(1000);

		for (int i = 0; i < d.getCurrentFace(); i++) {
			p.move(1);
			if (p.getLocation() == 0)
				p.setGold(p.getGold() + 5000);
			sleep(100);
		}

		d.setState(DiceWidget.State.UNROLLABLE);
		Place place = model.getPlace(p.getLocation());
		if (place instanceof BuyablePlace) {
			BuyablePlace bp = (BuyablePlace) place;
			Player owner = bp.getOwner();
			if (owner == null) { // may buy this place
				int b = model.getBuyPrice(bp.getLevel());
				if (b <= p.getGold() && waitDialog("Buy this place?")) {
					p.setGold(p.getGold() - b);
					bp.setOwner(p);
				}
			} else if (owner.equals(p)) { // may build on this place
				int b = model.getBuildPrice(bp.getLevel());
				if (b <= p.getGold() && bp.getHouses() < 5
						&& waitDialog("Build this place?")) {
					p.setGold(p.getGold() - b);
					bp.setHouses(bp.getHouses() + 1);
				}
			} else { // pay for staying
				int r = model.getRent(bp.getLevel(), bp.getHouses());
				if (p.getGold() < r) {
					bankrupt(p);
				} else {
					p.setGold(p.getGold() - r);
				}
				owner.setGold(owner.getGold() + r);
			}
		}

		if (!model.isGameOver()) {
			toNextTurn();
			d.setState(DiceWidget.State.ROLLABLE);
		}
		view.update();
	}

	/**
	 * Turns to next playable player.
	 */
	private void toNextTurn() {
		do {
			model.toNextTurn();
		} while (model.getPlayer(model.getTurnTo()).isBankrupt());
	}

	/**
	 * Declares the specific player as bankrupted.
	 */
	private void bankrupt(Player p) {
		p.setGold(0);
		p.setBankrupt(true);
		if (model.getPlayers().stream().filter(q -> !q.isBankrupt()).count() == 1) {
			model.setGameOver(true);
			return;
		}
		model.getBuyablePlaces().stream().filter(x -> x.getOwner() == p)
				.forEach(x -> {
					x.setOwner(null);
					x.setHouses(0);
				});
	}

	private void sleep(long t) {
		view.sleep(t);
	}

	private boolean waitDialog(String question) {
		return view.showDialog(question);
	}
}
