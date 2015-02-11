package org.twbbs.pccprogram.rich;

import static org.twbbs.pccprogram.rich.RichPanel.BLOCK;
import static org.twbbs.pccprogram.rich.RichPanel.paintString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class DiceRoller extends Widget {

	enum State {
		ROLLABLE, ROLLING, ROLLED, UNROLLABLE
	}

	private State state = State.UNROLLABLE;
	private int currentFace = 1;

	public DiceRoller(RichPanel view) {
		super(view);
		this.view.addMouseListener(new MouseAdapter() {
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = Objects.requireNonNull(state);
	}

	public void startRolling() {
		if (state != State.ROLLABLE)
			throw new IllegalStateException("state must be ROLLABLE");
		state = State.ROLLING;
		spin();
	}

	public void spin() {
		setCurrentFace((int) (Math.random() * 6) + 1);
	}

	public void stopRolling() {
		if (state != State.ROLLING)
			throw new IllegalStateException("state must be ROLLING");
		state = State.ROLLED;
	}

	public int getCurrentFace() {
		return currentFace;
	}

	public void setCurrentFace(int currentFace) {
		if (currentFace < 1 || currentFace > 6)
			throw new IllegalArgumentException("No such face");
		this.currentFace = currentFace;
	}
}
