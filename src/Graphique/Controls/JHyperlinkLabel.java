package Graphique.Controls;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

// Source: https://alvinalexander.com/blog/post/jfc-swing/emulate-hyperlink-in-java-with-jlinklabel/
public class JHyperlinkLabel extends JLabel {

	private static final long serialVersionUID = -8534897690709058779L;

	final Color DEFAULT_COLOR_HOVER = Color.RED;

	Runnable onClickCallback;
	Color hoverColor;
	Color normalColor;

	public JHyperlinkLabel(String text, Runnable onClickCallback) {
		super(text);
		this.hoverColor = DEFAULT_COLOR_HOVER;
		this.normalColor = getForeground();

		addMouseListener();
		this.setSize((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight());

		this.onClickCallback = onClickCallback;
	}

	public JHyperlinkLabel(String text, Color hoverColor, Runnable onClickCallback) {
		super(text);
		this.hoverColor = hoverColor;
		this.normalColor = getForeground();

		addMouseListener();
		this.setSize((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight());

		this.onClickCallback = onClickCallback;
	}

	public void setText(String text) {
		super.setText(text);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(2, getHeight() - 1, (int) getPreferredSize().getWidth() - 2, getHeight() - 1);
	}

	public void addMouseListener() {

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent me) {
				setForeground(normalColor);

				onClickCallback.run();
			}

			@Override
			public void mouseReleased(MouseEvent me) {

			}

			@Override
			public void mousePressed(MouseEvent me) {

			}

			@Override
			public void mouseEntered(MouseEvent me) {
				setForeground(hoverColor);
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				setForeground(normalColor);
				setCursor(Cursor.getDefaultCursor());
			}
		});
	}

	public Color getHoverColor() {
		return this.hoverColor;
	}

	public Color getNormalColor() {
		return this.normalColor;
	}

	public void setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public void setNormalColor(Color normalColor) {
		if (this.normalColor == getForeground()) {
			setForeground(normalColor);
		}
		this.normalColor = normalColor;
	}
}