package org.twbbs.pccprogram.rich;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Widget {

	protected final RichPanel view;
	protected final List<ActionListener> listeners = new ArrayList<>();

	public Widget(RichPanel view) {
		this.view = Objects.requireNonNull(view);
	}

	public abstract void paintWidget(Graphics2D g);

	public void addActionListener(ActionListener lis) {
		if (lis != null)
			listeners.add(lis);
	}

	public boolean removeActionListener(ActionListener lis) {
		return listeners.remove(lis);
	}

	protected void performAction(String command) {
		performAction(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				command));
	}

	protected void performAction(ActionEvent e) {
		for (ActionListener lis : listeners)
			lis.actionPerformed(e);
	}

	protected Point convertPoint(Point p) {
		Point p2 = new Point(p);
		p2.x -= (view.getWidth() - RichPanel.SIZE) / 2;
		p2.y -= (view.getHeight() - RichPanel.SIZE) / 2;
		p2.x -= 12 * RichPanel.BLOCK;
		p2.y -= 14 * RichPanel.BLOCK;
		return p2;
	}
}
