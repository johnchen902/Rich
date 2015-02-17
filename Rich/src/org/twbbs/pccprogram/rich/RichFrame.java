package org.twbbs.pccprogram.rich;

import javax.swing.JFrame;

/**
 * The frame that contains the entire game.
 * 
 * @author johnchen902
 */
@SuppressWarnings("serial")
public class RichFrame extends JFrame {

	private Control control;

	/**
	 * Constructor. Automatically shows up the frame but do not start the game.
	 */
	public RichFrame() {
		super("Rich");

		control = new Control();

		add(control.getView());

		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Get the {@code Control} object used by this game.
	 * 
	 * @return the {@code Control} object
	 */
	public Control getControl() {
		return control;
	}
}
