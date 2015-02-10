package org.twbbs.pccprogram.rich;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.twbbs.pccprogram.rich.RichPanel.BLOCK;

public class DiceRoller {

	enum State {
		ROLLABLE, ROLLING, ROLLED, UNROLLABLE
	}

	private RichPanel view;
	private State state = State.UNROLLABLE;
	private int currentFace = 1;
	private List<ActionListener> listeners = new ArrayList<>();

	public DiceRoller(RichPanel view) {
		this.view = Objects.requireNonNull(view);
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
		int mx = e.getX(), my = e.getY();
		// as in RichPanel.paint(Graphics g)
		mx -= (view.getWidth() - RichPanel.SIZE) / 2;
		my -= (view.getHeight() - RichPanel.SIZE) / 2;
		// as in RichPanel.paintControl(Graphics2D g)
		mx -= 12 * RichPanel.BLOCK;
		my -= 14 * RichPanel.BLOCK;

		if (mx < BLOCK / 2 || mx >= BLOCK * 5 / 2 || my < BLOCK / 2
				|| my >= BLOCK * 3 / 2)
			return;

		ActionEvent ae = new ActionEvent(DiceRoller.this,
				ActionEvent.ACTION_PERFORMED, "roll");
		for (ActionListener lis : listeners)
			lis.actionPerformed(ae);
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

	public void addActionListener(ActionListener lis) {
		if (lis != null)
			listeners.add(lis);
	}

	public boolean removeActionListener(ActionListener lis) {
		return listeners.remove(lis);
	}
}
