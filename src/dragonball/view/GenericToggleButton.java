package dragonball.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

//Uses code from http://ateraimemo.com/Swing/RoundButton.html

@SuppressWarnings("serial")
public class GenericToggleButton extends JToggleButton {

	protected static final int FOCUS_STROKE = 2;

	protected Shape shape;
	protected Shape border;
	protected Shape base;

	public GenericToggleButton() {
		setFocusable(false);
		setForeground(Color.white);
		setBorder(new LineBorder(Color.white, 1, true));
		super.setContentAreaFilled(false);
		setOpaque(false);

	}

	public GenericToggleButton(String text) {
		this();
		setText(text);
	}

	@Override
	public void updateUI() {
		super.updateUI();
		setContentAreaFilled(false);
		setFocusPainted(false);
		initShape();
	}

	protected void initShape() {
		if (!getBounds().equals(base)) {
			base = getBounds();
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
			border = new RoundRectangle2D.Float(FOCUS_STROKE, FOCUS_STROKE, getWidth() - 1 - FOCUS_STROKE * 2,
					getHeight() - 1 - FOCUS_STROKE * 2, getHeight(), getHeight());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		initShape();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (getModel().isSelected()) {
			g2.setPaint(new GradientPaint(new Point(0, 0), new Color(249, 185, 24), new Point(0, getHeight()),
					new Color(252, 217, 115)));
			g2.fill(shape);
			setForeground(Color.black);
		} else {
			g2.setPaint(new GradientPaint(new Point(0, 0), new Color(0, 0, 0), new Point(0, getHeight()),
					new Color(119, 119, 119)));
			g2.fill(shape);
			setForeground(Color.white);
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setColor(getBackground());
		super.paintComponent(g2);
		g2.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		initShape();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.draw(shape);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.dispose();
	}

	@Override
	public boolean contains(int x, int y) {
		initShape();
		return shape == null ? false : shape.contains(x, y);
	}

}
