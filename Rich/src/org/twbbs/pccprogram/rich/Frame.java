package org.twbbs.pccprogram.rich;

import javax.swing.JFrame;

/**
 * The frame that render the game.
 * 
 * @author johnchen902
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

	private Panel panel;

	/**
	 * Constructor. Do everything and is ready to show.
	 */
	public Frame() {
		super("Rich");

		add(panel = new Panel());

		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
	 * Get the containing panel
	 * 
	 * @return the panel
	 */
	public Panel getPanel() {
		return panel;
	}
}
