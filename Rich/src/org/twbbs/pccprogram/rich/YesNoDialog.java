package org.twbbs.pccprogram.rich;

import static org.twbbs.pccprogram.rich.RichPanel.BLOCK;
import static org.twbbs.pccprogram.rich.RichPanel.paintString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class YesNoDialog extends Widget {

	private String question = "?";
	private boolean enabled;
	private boolean selection;

	public YesNoDialog(RichPanel view) {
		super(view);
		this.view.addMouseListener(new MouseAdapter() {
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = Objects.requireNonNull(question);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}
}
