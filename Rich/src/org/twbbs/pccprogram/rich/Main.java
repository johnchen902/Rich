package org.twbbs.pccprogram.rich;

import javax.swing.SwingUtilities;

public class Main {

	private Main() {
	}

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
