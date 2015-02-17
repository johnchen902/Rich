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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RichFrame f = new RichFrame();
				f.getControl().startGame();
			}
		});
	}
}
