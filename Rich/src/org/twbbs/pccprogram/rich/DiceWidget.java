package org.twbbs.pccprogram.rich;

import static org.twbbs.pccprogram.rich.Panel.BLOCK;
import static org.twbbs.pccprogram.rich.Panel.paintString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * A widget that rolls a dice.
 * 
 * @author johnchen902
 */
public class DiceWidget extends Widget {

	/**
	 * The state of this widget.
	 * 
	 * @author johnchen902
	 */
	public enum State {
		/**
		 * The widget is waiting for the player to roll the dice.
		 */
		ROLLABLE,
		/**
		 * The dice is spinning. Shows the current (rapid-changing) face.
		 */
		ROLLING,
		/**
		 * The dice has stopped spinning. Shows the current face.
		 */
		ROLLED,
		/**
		 * The widget is unusable and shows nothing.
		 */
		UNROLLABLE
	}

	private State state = State.UNROLLABLE;
	private int currentFace = 1;

	/**
	 * Constructor. Mouse listener is added to the panel.
	 * 
	 * @param panel
	 *            the {@code RichPanel} that use this widget.
	 */
	public DiceWidget(Panel panel) {
		super(panel);
		this.panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick(e);
			}
		});
	}

	private void onClick(MouseEvent e) {
		if (state != State.ROLLABLE)
			return;
		Point p = convertPoint(e.getPoint());
		Rectangle bt = new Rectangle(BLOCK / 2, BLOCK / 2, BLOCK * 2, BLOCK);
		if (bt.contains(p))
			performAction("roll");
	}

	@Override
	public void paintWidget(Graphics2D g) {
		switch (state) {
		case ROLLABLE:
			paintRollButton(g);
			break;
		case ROLLING:
			paintRollingDice(g);
			break;
		case ROLLED:
			paintRolledDice(g);
			break;
		default:
			// paint nothing
			break;
		}
	}

	private void paintRollButton(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(BLOCK / 2, BLOCK / 2, BLOCK * 2, BLOCK);
		g.setColor(Color.BLACK);
		g.drawRect(BLOCK / 2, BLOCK / 2, BLOCK * 2, BLOCK);
		paintString(g, "Roll Dice", BLOCK / 2, BLOCK / 2, BLOCK * 2, BLOCK);
	}

	private void paintRollingDice(Graphics2D g) {
		// outer boundary
		g.drawRoundRect(BLOCK, BLOCK / 2, BLOCK, BLOCK, 7, 7);
		int face = getCurrentFace();
		if (face % 2 == 1) // center
			g.fillOval(BLOCK * 3 / 2 - 3, BLOCK - 3, 7, 7);
		if (face == 2 || face >= 4) {// left-top + right-bottom
			g.fillOval(BLOCK * 5 / 4 - 3, BLOCK * 3 / 4 - 3, 7, 7);
			g.fillOval(BLOCK * 7 / 4 - 3, BLOCK * 5 / 4 - 3, 7, 7);
		}
		if (face == 3 || face >= 4) { // left-bottom + right-top
			g.fillOval(BLOCK * 7 / 4 - 3, BLOCK * 3 / 4 - 3, 7, 7);
			g.fillOval(BLOCK * 5 / 4 - 3, BLOCK * 5 / 4 - 3, 7, 7);
		}
		if (face == 6) { // left + right
			g.fillOval(BLOCK * 5 / 4 - 3, BLOCK - 3, 7, 7);
			g.fillOval(BLOCK * 7 / 4 - 3, BLOCK - 3, 7, 7);
		}
	}

	private void paintRolledDice(Graphics2D g) {
		g.setColor(Color.RED);
		paintRollingDice(g);
		g.setColor(Color.BLACK);
	}

	/**
	 * Get the state of this widget.
	 * 
	 * @return the {@code State}
	 * @see #setState(State)
	 */
	public State getState() {
		return state;
	}

	/**
	 * Set the state of this widget.
	 * 
	 * @param state
	 *            the {@code State}
	 * @see #getState()
	 */
	public void setState(State state) {
		this.state = Objects.requireNonNull(state);
	}

	/**
	 * Set the state from {@code State.ROLLABLE} to {@code State.ROLLING} and
	 * {@code spin} once.
	 * 
	 * @throws IllegalStateException
	 *             if the current state is not {@code State.ROLLABLE}
	 * @see State#ROLLABLE
	 * @see State#ROLLING
	 * @see #spin()
	 * @see #setState(State)
	 */
	public void startRolling() throws IllegalStateException {
		if (state != State.ROLLABLE)
			throw new IllegalStateException("state must be ROLLABLE");
		state = State.ROLLING;
		spin();
	}

	/**
	 * Set the current face to a random face.
	 */
	public void spin() {
		setCurrentFace((int) (Math.random() * 6) + 1);
	}

	/**
	 * Set the state from {@code State.ROLLING} to {@code State.ROLLED}.
	 * 
	 * @throws IllegalStateException
	 *             if the current state is not {@code State.ROLLING}
	 * @see State#ROLLING
	 * @see State#ROLLED
	 * @see #setState(State)
	 */
	public void stopRolling() throws IllegalStateException {
		if (state != State.ROLLING)
			throw new IllegalStateException("state must be ROLLING");
		state = State.ROLLED;
	}

	/**
	 * Get the current face of the dice.
	 * 
	 * @return the face; must be between 1 to 6, inclusive
	 * @see #setCurrentFace(int)
	 */
	public int getCurrentFace() {
		return currentFace;
	}

	/**
	 * Set the current face of the dice.
	 * 
	 * @param currentFace
	 *            the face; must be between 1 to 6, inclusive
	 * @throws IllegalArgumentException
	 *             if the provided face is not between 1 to 6
	 * @see #getCurrentFace()
	 */
	public void setCurrentFace(int currentFace) throws IllegalArgumentException {
		if (currentFace < 1 || currentFace > 6)
			throw new IllegalArgumentException("No such face");
		this.currentFace = currentFace;
	}
}
