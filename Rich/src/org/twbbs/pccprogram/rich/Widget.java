package org.twbbs.pccprogram.rich;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A widget interacts with user and controller.
 * 
 * @author johnchen902
 */
public abstract class Widget {

	/**
	 * The {@code RichPanel} that use this widget.
	 */
	protected final Panel panel;

	/**
	 * The list of listeners.
	 */
	protected final List<ActionListener> listeners = new ArrayList<>();

	/**
	 * Constructor. Do nothing important.
	 * 
	 * @param panel
	 *            the {@code RichPanel} that use this widget.
	 */
	public Widget(Panel panel) {
		this.panel = Objects.requireNonNull(panel);
		this.panel.addWidget(this);
	}

	/**
	 * Paint itself on the {@code Graphics2D} provided. It is the only output to
	 * the user.
	 * 
	 * @param g
	 */
	public abstract void paintWidget(Graphics2D g);

	/**
	 * Adds an {@code ActionListener} to the button.
	 * 
	 * @param lis
	 *            the {@code ActionListener} to be added
	 */
	public void addActionListener(ActionListener lis) {
		if (lis != null)
			listeners.add(lis);
	}

	/**
	 * Removes an {@code ActionListener} from the button.
	 *
	 * @param lis
	 *            the listener to be removed
	 */
	public boolean removeActionListener(ActionListener lis) {
		return listeners.remove(lis);
	}

	/**
	 * Notify all listeners of an event with this command.
	 * 
	 * @param command
	 *            the command of the event
	 */
	protected void performAction(String command) {
		performAction(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				command));
	}

	/**
	 * Notify all listeners of this event.
	 * 
	 * @param e
	 *            the event
	 */
	protected void performAction(ActionEvent e) {
		for (ActionListener lis : listeners)
			lis.actionPerformed(e);
	}

	/**
	 * Convert the point from the coordinate of the panel to the coordinate of
	 * the area used by widgets.
	 * 
	 * @param p
	 *            the point to be convert from
	 * @return the point converted to
	 */
	protected Point convertPoint(Point p) {
		Point p2 = new Point(p);
		p2.x -= (panel.getWidth() - Panel.SIZE) / 2;
		p2.y -= (panel.getHeight() - Panel.SIZE) / 2;
		p2.x -= 12 * Panel.BLOCK;
		p2.y -= 14 * Panel.BLOCK;
		return p2;
	}
}
