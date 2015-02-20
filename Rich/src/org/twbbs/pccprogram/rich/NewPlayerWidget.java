package org.twbbs.pccprogram.rich;

import static org.twbbs.pccprogram.rich.Panel.BLOCK;
import static org.twbbs.pccprogram.rich.Panel.paintString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A widget that add new players.
 * 
 * @author johnchen902
 */
public class NewPlayerWidget extends Widget {

	private boolean enabled;

	/**
	 * Constructor. Mouse listener is added to the panel.
	 * 
	 * @param panel
	 *            the {@code Panel} that use this widget.
	 */
	public NewPlayerWidget(Panel panel) {
		super(panel);
		this.panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick(e);
			}
		});
	}

	private void onClick(MouseEvent e) {
		if (!enabled)
			return;
		Point p = convertPoint(e.getPoint());
		Rectangle bt = new Rectangle(BLOCK / 2, BLOCK * 2, BLOCK * 2, BLOCK);
		if (bt.contains(p))
			performAction("newPlayer");
	}

	@Override
	public void paintWidget(Graphics2D g) {
		if (enabled) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(BLOCK / 2, BLOCK * 2, BLOCK * 2, BLOCK);
			g.setColor(Color.BLACK);
			g.drawRect(BLOCK / 2, BLOCK * 2, BLOCK * 2, BLOCK);
			paintString(g, "New Player", BLOCK / 2, BLOCK * 2, BLOCK * 2, BLOCK);
		}
	}

	/**
	 * Determines whether this widget is enabled. This widget is only visible
	 * and usable when enabled.
	 * 
	 * @return {@code true} if the widget is enabled, {@code false} otherwise
	 * @see #setEnabled(boolean)
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables this widget, depending on the value of the parameter
	 * b.
	 * 
	 * @param enabled
	 *            If {@code true}, this widget is enabled; otherwise this widget
	 *            is disabled
	 * @see #isEnabled()
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
