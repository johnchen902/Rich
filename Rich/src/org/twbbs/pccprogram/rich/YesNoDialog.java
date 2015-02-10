package org.twbbs.pccprogram.rich;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.twbbs.pccprogram.rich.RichPanel.BLOCK;

public class YesNoDialog {

	private RichPanel view;
	private String question = "?";
	private boolean enabled;
	private boolean selection;

	private List<ActionListener> listeners = new ArrayList<>();

	public YesNoDialog(RichPanel view) {
		this.view = Objects.requireNonNull(view);
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
		int mx = e.getX(), my = e.getY();
		// as in RichPanel.paint(Graphics g)
		mx -= (view.getWidth() - RichPanel.SIZE) / 2;
		my -= (view.getHeight() - RichPanel.SIZE) / 2;
		// as in RichPanel.paintControl(Graphics2D g)
		mx -= 12 * RichPanel.BLOCK;
		my -= 14 * RichPanel.BLOCK;

		Rectangle yes = new Rectangle(BLOCK * 6 / 2, BLOCK / 2, BLOCK, BLOCK);
		Rectangle no = new Rectangle(BLOCK * 9 / 2, BLOCK / 2, BLOCK, BLOCK);

		if (yes.contains(mx, my))
			setSelection(true);
		else if (no.contains(mx, my))
			setSelection(false);
		else
			return;

		ActionEvent ae = new ActionEvent(YesNoDialog.this,
				ActionEvent.ACTION_PERFORMED, "selection");
		for (ActionListener lis : listeners)
			lis.actionPerformed(ae);
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

	public void addActionListener(ActionListener lis) {
		if (lis != null)
			listeners.add(lis);
	}

	public boolean removeActionListener(ActionListener lis) {
		return listeners.remove(lis);
	}
}
