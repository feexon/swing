package com.sxan.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class ClosableTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = -5154435928212725041L;

	public ClosableTabbedPane() {
		addMouseListener(new TabHandler());
	}

	public void addTab(String title, Component component) {
		super.addTab(title, new CloseIcon(), component);
	}

	private class TabHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JTabbedPane tabs = (JTabbedPane) e.getSource();
			int tabNumber = getUI().tabForCoordinate(tabs, e.getX(), e.getY());
			if (tabNumber < 0) {
				return;
			}
			if ( getIconAt(tabNumber) instanceof CloseIcon ) {
				if (((CloseIcon) getIconAt(tabNumber)).contains(new Point(offsetX(e.getX()), e.getY()))) {
					tabs.removeTabAt(tabNumber);
				}
			}
		}
	}

	private int offsetX(int x) {
		switch (getTabLayoutPolicy()) {
			case JTabbedPane.SCROLL_TAB_LAYOUT:
				return x - getUI().getTabBounds(this, 0).x;
			default:
				return x;
		}

	}

	public class CloseIcon implements Icon {

		private int x_pos;
		private int y_pos;
		private int width;
		private int height;

		public CloseIcon() {
			width = 16;
			height = 16;
		}

		public boolean contains(Point point) {
			return getBounds().contains(point);
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			this.x_pos = x;
			this.y_pos = y;
			Color col = g.getColor();
			g.setColor(Color.RED.darker());
			int y_p = y + 2;
			g.drawLine(x + 1, y_p, x + 12, y_p);
			g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
			g.drawLine(x, y_p + 1, x, y_p + 12);
			g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
			g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
			g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
			g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
			g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
			g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
			g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
			g.setColor(col);
		}

		public int getIconWidth() {
			return width;
		}

		public int getIconHeight() {
			return height;
		}

		public Rectangle getBounds() {
			return new Rectangle(x_pos, y_pos, width, height);
		}
	}
}
