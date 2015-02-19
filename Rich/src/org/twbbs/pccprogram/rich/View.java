package org.twbbs.pccprogram.rich;

import java.awt.SecondaryLoop;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * The view renders everything needed in the game.
 * 
 * @author johnchen902
 */
public class View {

	private Frame frame;
	private SecondaryLoop loop;
	private DiceWidget dice;
	private DialogWidget dialog;

	/**
	 * Constructor. After construction, in order to render the game,
	 * {@code setModel} and {@code show} must be called, in order.
	 * 
	 * @see #setModel(Model)
	 * @see #show()
	 */
	public View() {
		frame = new Frame();
		loop = frame.getToolkit().getSystemEventQueue().createSecondaryLoop();

		Panel panel = frame.getPanel();
		dice = new DiceWidget(panel);
		dialog = new DialogWidget(panel);
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
	 * Get the {@code DiceWidget} in use.
	 * 
	 * @return a {@code DiceWidget}
	 */
	public DiceWidget getDiceWidget() {
		return dice;
	}

	/**
	 * Get the {@code DialogWidget} in use.
	 * 
	 * @return a {@code DialogWidget}
	 */
	public DialogWidget getDialogWidget() {
		return dialog;
	}

	/**
	 * Show the view.
	 */
	public void show() {
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
