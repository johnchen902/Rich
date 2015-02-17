package org.twbbs.pccprogram.rich;

import java.awt.SecondaryLoop;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.Timer;

import javax.swing.SwingUtilities;

/**
 * The controller of this game. All the logics.
 * 
 * @author johnchen902
 */
public class Control {

	private Model model;
	private RichPanel view;
	private SecondaryLoop loop;

	/**
	 * Constructor. Initialize things needed.
	 */
	public Control() {
		model = new Model();

		if (SwingUtilities.isEventDispatchThread()) {
			initInEventDispatchThread();
		} else {
			try {
				SwingUtilities.invokeAndWait(this::initInEventDispatchThread);
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void initInEventDispatchThread() {
		view = new RichPanel(model);
		loop = view.getToolkit().getSystemEventQueue().createSecondaryLoop();
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
	 * Get the panel used by this game.
	 * 
	 * @return the panel
	 */
	public RichPanel getView() {
		return view;
	}

	/**
	 * Starts the game.
	 */
	public void startGame() {
		model.initDefault();
		view.repaint();

		DiceRoller dice = view.getDiceRoller();
		dice.setState(DiceRoller.State.ROLLABLE);
		dice.addActionListener(e -> turn());
	}

	/**
	 * A single turn of the game.
	 */
	private void turn() {
		DiceRoller d = view.getDiceRoller();
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

		d.setState(DiceRoller.State.UNROLLABLE);
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
			d.setState(DiceRoller.State.ROLLABLE);
		}
		view.repaint();
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
		}
	}

	/**
	 * Waits for the specific amount of time before returning.
	 * 
	 * @param t
	 *            the amount of time to sleep in milliseconds
	 */
	private void sleep(long t) {
		view.repaint();
		Timer timer = new Timer((int) t, e -> loop.exit());
		timer.setRepeats(false);
		timer.start();
		loop.enter();
	}

	/**
	 * Waits for the player to respond to a yes-no dialog.
	 * 
	 * @param question
	 *            the question to ask
	 * @return the player's choice. {@code true} if user chose "Yes",
	 *         {@code false} if user chose "No"
	 */
	private boolean waitDialog(String question) {
		YesNoDialog dialog = view.getDialog();
		ActionListener lis = e -> loop.exit();
		dialog.addActionListener(lis);
		dialog.setQuestion(question);
		dialog.setEnabled(true);
		view.repaint();
		loop.enter();
		dialog.setEnabled(false);
		dialog.removeActionListener(lis);
		return dialog.getSelection();
	}
}
