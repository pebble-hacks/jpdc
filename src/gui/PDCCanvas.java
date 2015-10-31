package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PDCCanvas extends JPanel {
	
	private static final Dimension CANVAS_SIZE = new Dimension(180, 180);
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, CANVAS_SIZE.width, CANVAS_SIZE.height);
	}
	
	public PDCCanvas() {
		setPreferredSize(CANVAS_SIZE);
		invalidate();
	}
	
	public void reset() {
		
	}

}
