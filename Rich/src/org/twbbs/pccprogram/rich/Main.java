package org.twbbs.pccprogram.rich;

import javax.swing.SwingUtilities;

/**
 * The class that contains the main method, literally.
 * 
 * @author johnchen902
 */
public class Main {

	private Main() {
	}

	/**
	 * The main method. Create the frame and start the game.
	 * 
	 * @param args
	 *            currently useless
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Control control = new Control();
			View view = new View();
			view.setModel(control.getModel());
			control.setView(view);
			view.show();
		});
	}
}
