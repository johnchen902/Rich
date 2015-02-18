package org.twbbs.pccprogram.rich;

import static org.twbbs.pccprogram.rich.Panel.BLOCK;
import static org.twbbs.pccprogram.rich.Panel.paintString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * A widget that shows a yes-no dialog.
 * 
 * @author johnchen902
 */
public class YesNoDialog extends Widget {

	private String question = "?";
	private boolean enabled;
	private boolean selection;

	/**
	 * Constructor. Mouse listener is added to the panel.
	 * 
	 * @param panel
	 *            the {@code RichPanel} that use this widget.
	 */
	public YesNoDialog(Panel panel) {
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
		Rectangle yes = new Rectangle(BLOCK * 6 / 2, BLOCK / 2, BLOCK, BLOCK);
		Rectangle no = new Rectangle(BLOCK * 9 / 2, BLOCK / 2, BLOCK, BLOCK);
		if (yes.contains(p) || no.contains(p)) {
			setSelection(yes.contains(p));
			performAction("selection");
		}
	}

	@Override
	public void paintWidget(Graphics2D g) {
		if (enabled) {
			paintString(g, question, 0, BLOCK / 2, BLOCK * 3, BLOCK);

			g.setColor(Color.GREEN);
			g.fillRect(BLOCK * 6 / 2, BLOCK / 2, BLOCK, BLOCK);
			g.setColor(Color.BLACK);
			g.drawRect(BLOCK * 6 / 2, BLOCK / 2, BLOCK, BLOCK);
			paintString(g, "Yes", BLOCK * 6 / 2, BLOCK / 2, BLOCK, BLOCK);

			g.setColor(Color.RED);
			g.fillRect(BLOCK * 9 / 2, BLOCK / 2, BLOCK, BLOCK);
			g.setColor(Color.BLACK);
			g.drawRect(BLOCK * 9 / 2, BLOCK / 2, BLOCK, BLOCK);
			paintString(g, "No", BLOCK * 9 / 2, BLOCK / 2, BLOCK, BLOCK);
		}
	}

	/**
	 * Get the question asked of player
	 * 
	 * @return the question
	 * @see #setQuestion(String)
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Set the question asked of player
	 * 
	 * @param question
	 *            the question
	 * @see #getQuestion()
	 */
	public void setQuestion(String question) {
		this.question = Objects.requireNonNull(question);
	}

	/**
	 * Determines whether this dialog is enabled. This dialog is only visible
	 * and usable when enabled.
	 * 
	 * @return {@code true} if the dialog is enabled, {@code false} otherwise
	 * @see #setEnabled(boolean)
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables this dialog, depending on the value of the parameter
	 * b.
	 * 
	 * @param enabled
	 *            If {@code true}, this dialog is enabled; otherwise this dialog
	 *            is disabled
	 * @see #isEnabled()
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the selection of player
	 * 
	 * @return {@code true} if user chose "Yes", {@code false} if user chose
	 *         "No"
	 * @see #setSelection(boolean)
	 */
	public boolean getSelection() {
		return selection;
	}

	/**
	 * Overwrite the selection of player
	 * 
	 * @param selection
	 *            {@code true} if choosing "Yes", {@code false} if choosing "No"
	 * @see #getSelection()
	 */
	public void setSelection(boolean selection) {
		this.selection = selection;
	}
}
