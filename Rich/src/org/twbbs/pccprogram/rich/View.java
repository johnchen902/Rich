package org.twbbs.pccprogram.rich;

import java.awt.SecondaryLoop;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class View {

	private Frame frame;
	private SecondaryLoop loop;
	private DiceRoller dice;
	private YesNoDialog dialog;

	public View() {
		frame = new Frame();
		loop = frame.getToolkit().getSystemEventQueue().createSecondaryLoop();

		Panel panel = frame.getPanel();
		dice = new DiceRoller(panel);
		dialog = new YesNoDialog(panel);
	}

	/**
	 * Set the underling model to be used by this panel
	 * 
	 * @param model
	 *            the model
	 */
	public void setModel(Model model) {
		frame.getPanel().setModel(model);
	}

	/**
	 * Get the {@code DiceRoller} widget in use.
	 * 
	 * @return a {@code DiceRoller} widget
	 */
	public DiceRoller getDiceRoller() {
		return dice;
	}

	/**
	 * Get the {@code YesNoDialog} widget in use.
	 * 
	 * @return a {@code YesNoDialog} widget
	 */
	public YesNoDialog getDialog() {
		return dialog;
	}

	/**
	 * Starts the game.
	 */
	public void startGame() {
		frame.setVisible(true);
	}

	/**
	 * Update the view.
	 */
	public void update() {
		frame.getPanel().repaint();
	}

	/**
	 * Waits for the specific amount of time before returning.
	 * 
	 * @param t
	 *            the amount of time to sleep in milliseconds
	 */
	public void sleep(long t) {
		update();
		Timer timer = new Timer((int) t, e -> loop.exit());
		timer.setRepeats(false);
		timer.start();
		loop.enter();
	}

	/**
	 * Waits for the player to respond to a yes-no dialog.
	 * 
	 * @param question
	 *            the question to ask
	 * @return the player's choice. {@code true} if user chose "Yes",
	 *         {@code false} if user chose "No"
	 */
	public boolean showDialog(String question) {
		ActionListener lis = e -> loop.exit();
		dialog.addActionListener(lis);
		dialog.setQuestion(question);
		dialog.setEnabled(true);
		update();
		loop.enter();
		dialog.setEnabled(false);
		dialog.removeActionListener(lis);
		return dialog.getSelection();
	}
}
