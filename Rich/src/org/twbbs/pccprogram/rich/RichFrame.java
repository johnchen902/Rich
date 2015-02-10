package org.twbbs.pccprogram.rich;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class RichFrame extends JFrame {

	private Control control;

	public RichFrame() {
		super("Rich");

		control = new Control();

		add(control.getView());

		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Control getControl() {
		return control;
	}
}
