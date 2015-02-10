package org.twbbs.pccprogram.rich;

import java.awt.SecondaryLoop;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class Control {

	private Model model;
	private RichPanel view;
	private SecondaryLoop loop;
	private Timer timer;

	public Control() {
		model = new Model();
		timer = new Timer();

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

	public Model getModel() {
		return model;
	}

	public RichPanel getView() {
		return view;
	}

	public void startGame() {
		model.initDefault();
		view.repaint();

		DiceRoller dice = view.getDiceRoller();
		dice.setState(DiceRoller.State.ROLLABLE);
		dice.addActionListener(e -> turn());
	}

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

	private void toNextTurn() {
		do {
			model.toNextTurn();
		} while (model.getPlayer(model.getTurnTo()).isBankrupt());
	}

	private void bankrupt(Player p) {
		p.setGold(0);
		p.setBankrupt(true);
		if (model.getPlayers().stream().filter(q -> !q.isBankrupt()).count() == 1) {
			model.setGameOver(true);
		}
	}

	private void sleep(long t) {
		view.repaint();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				loop.exit();
			}
		}, t);
		loop.enter();
	}

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
