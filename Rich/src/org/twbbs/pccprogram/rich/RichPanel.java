package org.twbbs.pccprogram.rich;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RichPanel extends JPanel {

	public static final int N_BLOCK = 21; // number of blocks
	public static final int BLOCK = 32; // block size
	public static final int SIZE = N_BLOCK * BLOCK;

	private Model model;

	private DiceRoller diceRoller;
	private YesNoDialog dialog;

	public RichPanel(Model model) {
		this.model = model;
		this.diceRoller = new DiceRoller(this);
		this.dialog = new YesNoDialog(this);
	}

	public DiceRoller getDiceRoller() {
		return diceRoller;
	}

	public YesNoDialog getDialog() {
		return dialog;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}

	@Override
	public void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D) g0;
		g.translate((getWidth() - SIZE) / 2, (getHeight() - SIZE) / 2);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SIZE, SIZE);

		g.setColor(Color.BLACK);
		Stroke defaultStroke = g.getStroke();
		g.setStroke(new BasicStroke(3f));
		g.drawRect(-2, -2, SIZE + 3, SIZE + 3);
		g.setStroke(defaultStroke);

		paintOuterLoop(g);
		paintInnerLoop(g);

		g.drawLine(12 * BLOCK, 3 * BLOCK, 12 * BLOCK, 18 * BLOCK);
		g.drawLine(3 * BLOCK, 9 * BLOCK, 12 * BLOCK, 9 * BLOCK);
		g.drawLine(12 * BLOCK, 8 * BLOCK, 18 * BLOCK, 8 * BLOCK);
		g.drawLine(12 * BLOCK, 14 * BLOCK, 18 * BLOCK, 14 * BLOCK);

		paintSaleTable(g);
		paintStatTable(g);

		paintControl(g);
	}

	private void paintOuterLoop(Graphics2D g) {
		g.drawRect(3 * BLOCK, 3 * BLOCK, 15 * BLOCK, 15 * BLOCK);

		for (int loc = 3 * BLOCK; loc <= 18 * BLOCK; loc += BLOCK) {
			g.drawLine(loc, 0, loc, 3 * BLOCK);
			g.drawLine(loc, 18 * BLOCK, loc, SIZE);
			g.drawLine(0, loc, 3 * BLOCK, loc);
			g.drawLine(18 * BLOCK, loc, SIZE, loc);
		}

		for (int i = 0; i < 15; i++) {
			paintPlace(g, (17 - i) * BLOCK, 18 * BLOCK, 0, i + 1);
			paintPlace(g, 3 * BLOCK, (17 - i) * BLOCK, Math.PI / 2, i + 17);
			paintPlace(g, (4 + i) * BLOCK, 3 * BLOCK, Math.PI, i + 33);
			paintPlace(g, 18 * BLOCK, (4 + i) * BLOCK, Math.PI * 1.5, i + 53);
		}
		paintCorner(g, 18 * BLOCK, 18 * BLOCK, 0);
		paintCorner(g, 0, 18 * BLOCK, 16);
		paintCorner(g, 0, 0, 32);
		paintCorner(g, 18 * BLOCK, 0, 48);
	}

	private void paintPlace(Graphics2D g0, int tx, int ty, double rt,
			int placeId) {
		Graphics2D g = (Graphics2D) g0.create();
		g.translate(tx, ty);
		g.rotate(rt);
		paintPlace(g, placeId);
		g.dispose();
	}

	private void paintPlace(Graphics2D g, int placeId) {
		g.drawLine(0, BLOCK, BLOCK, BLOCK);
		g.drawLine(BLOCK / 2, BLOCK, BLOCK / 2, BLOCK * 3);
		g.drawLine(BLOCK / 2, BLOCK * 2, BLOCK, BLOCK * 2);
		g.drawLine(0, BLOCK * 5 / 2, BLOCK / 2, BLOCK * 5 / 2);

		paintPlayers(g, placeId, 0, 0, BLOCK, BLOCK);

		BuyablePlace place = (BuyablePlace) model.getPlace(placeId);
		paintString(g, "" + place.getLevel(), 1, BLOCK * 5 / 2 + 1, BLOCK / 2,
				BLOCK / 2);
		if (place.getOwner() != null)
			paintString(g, place.getOwner().getId(), BLOCK / 2 + 1, BLOCK,
					BLOCK / 2, BLOCK);
		if (place.getHouses() > 0)
			paintString(g, "" + place.getHouses(), BLOCK / 2 + 1, BLOCK * 2,
					BLOCK / 2, BLOCK);

		g.translate(BLOCK / 2, BLOCK);
		g.rotate(Math.PI / 2);
		paintString(g, place.getName(), 0, 0, BLOCK * 3 / 2, BLOCK / 2);
	}

	private void paintCorner(Graphics2D g0, int tx, int ty, int placeId) {
		Graphics2D g = (Graphics2D) g0.create();
		g.translate(tx, ty);
		paintCorner(g, placeId);
		g.dispose();
	}

	private void paintCorner(Graphics2D g, int placeId) {
		final int px, py;
		switch (placeId) {
		case 0:
			px = py = 0;
			break;
		case 16:
			px = 2 * BLOCK;
			py = 0;
			break;
		case 32:
			px = py = 2 * BLOCK;
			break;
		case 48:
			px = 0;
			py = 2 * BLOCK;
			break;
		default:
			throw new IllegalArgumentException("not a corner");
		}

		g.drawRect(px, py, BLOCK, BLOCK);

		paintPlayers(g, placeId, px, py, BLOCK, BLOCK);

		Place place = model.getPlace(placeId);
		paintString(g, place.getName(), 0, 0, BLOCK * 3, BLOCK * 3);
	}

	private void paintInnerLoop(Graphics2D g) {
		g.drawLine(BLOCK * 18, BLOCK * 3, BLOCK * 35 / 2, BLOCK * 7 / 2);
		g.drawLine(BLOCK * 35 / 2, BLOCK * 7 / 2, BLOCK * 25 / 2, BLOCK * 7 / 2);
		g.drawLine(BLOCK * 25 / 2, BLOCK * 7 / 2, BLOCK * 25 / 2, BLOCK * 9 / 2);
		g.drawLine(BLOCK * 25 / 2, BLOCK * 9 / 2, BLOCK * 13, BLOCK * 9 / 2);

		g.drawRect(BLOCK * 13, BLOCK * 4, BLOCK * 4, BLOCK * 3);
		for (int i = 1; i <= 3; i++)
			g.drawLine(BLOCK * (13 + i), BLOCK * 4, BLOCK * (13 + i), BLOCK * 7);
		for (int i = 0; i < 4; i++) {
			Graphics2D g1 = (Graphics2D) g.create();
			g1.translate(BLOCK * (13 + i), BLOCK * 4);
			paintInnerPlace(g1, i + 49);
			g1.dispose();
		}

		g.drawLine(BLOCK * 17, BLOCK * 9 / 2, BLOCK * 18, BLOCK * 7 / 2);
	}

	private void paintInnerPlace(Graphics2D g, int placeId) {
		g.drawLine(0, BLOCK, BLOCK, BLOCK);
		g.drawLine(BLOCK / 2, BLOCK, BLOCK / 2, BLOCK * 3);

		paintPlayers(g, placeId, 0, 0, BLOCK, BLOCK);

		g.translate(BLOCK / 2, BLOCK);
		g.rotate(Math.PI / 2);
		Place place = model.getPlace(placeId);
		paintString(g, place.getName(), 0, 0, BLOCK * 2, BLOCK / 2);
	}

	private void paintPlayers(Graphics2D g, int placeId, int x, int y,
			int width, int height) {
		model.getPlayers().stream().filter(p -> p.getLocation() == placeId)
				.map(Player::getId).reduce(String::concat).ifPresent(s -> {
					Font f = g.getFont();
					g.setFont(f.deriveFont(20f));
					paintString(g, s, x, y, width, height);
					g.setFont(f);
				});
	}

	private void paintSaleTable(Graphics2D g) {
		g = (Graphics2D) g.create();
		g.translate(3 * BLOCK, 3 * BLOCK);
		g.clipRect(1, 1, 9 * BLOCK - 1, 6 * BLOCK - 1);

		g.setColor(Color.BLUE);

		for (int i = 1; i <= 5; i++)
			g.drawLine(0, i * BLOCK, 9 * BLOCK, i * BLOCK);
		g.drawLine(0, 0, BLOCK, BLOCK);
		g.drawLine(BLOCK * 2, BLOCK / 2, BLOCK * 7, BLOCK / 2);
		for (int i = 1; i <= 8; i++)
			g.drawLine(BLOCK * i, (i >= 3 && i <= 6) ? BLOCK / 2 : 0,
					BLOCK * i, BLOCK * 6);

		paintString(g, "Lv", 0, BLOCK / 2, BLOCK * 5 / 8, BLOCK / 2);
		paintString(g, "Stat", BLOCK * 3 / 8, 0, BLOCK * 5 / 8, BLOCK / 2);
		paintString(g, "Rent", BLOCK, 0, BLOCK, BLOCK);
		paintString(g, "Rent (with __ houses)", BLOCK * 2, 0, BLOCK * 5,
				BLOCK / 2);
		for (int i = 1; i <= 5; i++)
			paintString(g, Integer.toString(i), BLOCK * (1 + i), BLOCK / 2,
					BLOCK, BLOCK / 2);
		paintString(g, "Buy", BLOCK * 7, 0, BLOCK, BLOCK);
		paintString(g, "Build", BLOCK * 8, 0, BLOCK, BLOCK);

		for (int lv = 1; lv <= 5; lv++) {
			paintString(g, "Lv " + lv, 0, BLOCK * lv, BLOCK, BLOCK);
			for (int houses = 0; houses <= 5; houses++)
				paintString(g, toMoneyString(model.getRent(lv, houses)), BLOCK
						* (1 + houses), BLOCK * lv, BLOCK, BLOCK);
			paintString(g, toMoneyString(model.getBuyPrice(lv)), BLOCK * 7,
					BLOCK * lv, BLOCK, BLOCK);
			paintString(g, toMoneyString(model.getBuildPrice(lv)), BLOCK * 8,
					BLOCK * lv, BLOCK, BLOCK);
		}

		g.dispose();
	}

	private String toMoneyString(int $) {
		String s1 = "$" + $;
		String s2;
		if ($ % 1000 == 0)
			s2 = "$" + $ / 1000 + "k";
		else
			s2 = "$" + $ / 1000.0 + "k";
		return s1.length() < s2.length() ? s1 : s2;
	}

	private void paintStatTable(Graphics2D g) {
		g = (Graphics2D) g.create();
		g.translate(3 * BLOCK, 9 * BLOCK);
		g.clipRect(1, 1, 9 * BLOCK - 1, 9 * BLOCK - 1);

		g.setColor(Color.YELLOW);
		g.fillRect(0, BLOCK * (model.getTurnTo() + 1), 9 * BLOCK, BLOCK);

		g.setColor(Color.BLUE);

		int th = model.getPlayerCount() + 1;

		for (int i = 1; i <= th; i++)
			g.drawLine(0, i * BLOCK, 9 * BLOCK, i * BLOCK);
		g.drawLine(0, 0, BLOCK, BLOCK);
		paintString(g, "ID", 0, BLOCK / 2, BLOCK * 5 / 8, BLOCK / 2);
		paintString(g, "Stat", BLOCK * 3 / 8, 0, BLOCK * 5 / 8, BLOCK / 2);
		g.drawLine(BLOCK, 0, BLOCK, th * BLOCK);
		paintString(g, "Gold", BLOCK, 0, 2 * BLOCK, BLOCK);
		g.drawLine(3 * BLOCK, 0, 3 * BLOCK, th * BLOCK);
		paintString(g, "State", 3 * BLOCK, 0, 2 * BLOCK, BLOCK);
		g.drawLine(5 * BLOCK, 0, 5 * BLOCK, th * BLOCK);

		for (int i = 0; i < model.getPlayerCount(); i++) {
			Player p = model.getPlayer(i);
			paintString(g, p.getId(), 0, (i + 1) * BLOCK, BLOCK, BLOCK);
			String gold = Integer.toString(p.getGold());
			paintString(g, gold, BLOCK, (i + 1) * BLOCK, 2 * BLOCK, BLOCK);
			String state = p.isBankrupt() ? "Bankrupt" : "OK";
			paintString(g, state, 3 * BLOCK, (i + 1) * BLOCK, 2 * BLOCK, BLOCK);
		}

		g.dispose();
	}

	private void paintControl(Graphics2D g) {
		g = (Graphics2D) g.create();
		g.translate(12 * BLOCK, 14 * BLOCK);
		g.clipRect(1, 1, 6 * BLOCK - 1, 4 * BLOCK - 1);

		if (model.isGameOver()) {
			Font f = g.getFont();
			g.setColor(Color.GREEN);
			g.setFont(f.deriveFont(32f).deriveFont(Font.BOLD));
			paintString(g, "Game Over", 0, 0, 6 * BLOCK, 4 * BLOCK);
			g.setFont(f);
			g.setColor(Color.BLACK);
		}

		diceRoller.paintWidget(g);

		dialog.paintWidget(g);

		g.dispose();
	}

	// Automatically adjust font size if out of bounds
	public static void paintString(Graphics2D g, String s, int x, int y,
			int width, int height) {
		Font font = g.getFont();
		int sz, textW = Integer.MAX_VALUE;
		for (sz = font.getSize(); textW > width && sz > 0; sz--)
			textW = g.getFontMetrics(font.deriveFont((float) sz))
					.stringWidth(s);
		sz++;

		if (sz == font.getSize()) {
			paintString0(g, s, x, y, width, height);
			return;
		}

		// Color c = g.getColor();
		// g.setColor(Color.RED);
		g.setFont(font.deriveFont((float) sz));
		paintString0(g, s, x, y, width, height);
		g.setFont(font);
		// g.setColor(c);
	}

	private static void paintString0(Graphics2D g, String s, int x, int y,
			int width, int height) {
		FontMetrics fm = g.getFontMetrics();
		g.drawString(s, x + (width - fm.stringWidth(s)) / 2,
				y + (height - fm.getHeight()) / 2 + fm.getAscent());
	}
}
