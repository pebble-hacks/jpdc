package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import pdc.PDC;
import pdc.PDCI;

public class PDCCanvas extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final Dimension CANVAS_SIZE = new Dimension(400, 400);
	private static final int 
		GRID_SIZE = 8,
		CROSSHAIR_WIDTH = GRID_SIZE / 2,
		CROSSHAIR_RADIUS = GRID_SIZE;
	
	private JPDCGUI gui;
	private PDCI image;
	private PDC currentCommand;
	private Point crossHair = new Point();
	
	public PDCCanvas(JPDCGUI gui) {
		this.gui = gui;
		setPreferredSize(CANVAS_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		Dimension viewBox = new Dimension(CANVAS_SIZE.width / GRID_SIZE, CANVAS_SIZE.height / GRID_SIZE);
		System.out.println("viewbox: " + viewBox.toString());
		image = new PDCI(viewBox);
		
		repaint();
	}
	
	private Point getNearestGridDisplayPoint(Point p) {
		Point result = new Point(p.x * GRID_SIZE, p.y * GRID_SIZE);
		System.out.println("will display: " + p.toString() + " -> " + result.toString());
		return result;
	}
	
	private Point getNearestGridPoint(Point p) {
		Point result = new Point(
				(int)Math.floor((float)p.x / (float)GRID_SIZE), 
				(int)Math.floor((float)p.y / (float)GRID_SIZE));
		System.out.println("will store: " + p.toString() + " -> " + result.toString());
		return result;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// Background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, CANVAS_SIZE.width, CANVAS_SIZE.height);
		
		// Grid
		g2d.setColor(Color.LIGHT_GRAY);
		Dimension size = getSize();
		
		// Horizontal
		for(int y = 0; y < size.height; y += GRID_SIZE) {
			g2d.drawLine(0, y, size.width, y);
		}
		
		// Vertical
		for(int x = 0; x < size.width; x += GRID_SIZE) {
			g2d.drawLine(x, 0, x, size.height);
		}
		
		// Draw commands so far
		ArrayList<PDC> commandList = image.getCommandList();
		for(PDC command : commandList) {
			drawCommand(g2d, command);
		}
		
		// In-progress command
		if(currentCommand != null) {
			drawCommand(g2d, currentCommand);
		}
	}
	
	private void drawCommand(Graphics2D g2d, PDC command) {
		// Set the stroke width and fill color
		g2d.setStroke(new BasicStroke(command.getStrokeWidth()));
		
		if(command.getType() == PDC.TYPE_PATH) {
			// Get all points
			ArrayList<Point> points = command.getPointArray();
			
			// Draw filled?
			if(command.getPathOpenRadius() == PDC.PATH_CLOSED) {
				// Make array of x and y
				int[] xArr = new int[points.size()];
				int[] yArr = new int[points.size()];
			    for(int i = 0; i < points.size(); i++) {
			    	// Modify to be shown on the enlarged grid
			        xArr[i] = getNearestGridDisplayPoint(points.get(i)).x;
			        yArr[i] = getNearestGridDisplayPoint(points.get(i)).y;
			    }
			    g2d.setColor(command.getFillColor());
				g2d.drawPolygon(xArr, yArr, points.size());
			}
			
			// Draw lines of outline
			g2d.setColor(command.getStrokeColor());
			int strokeWidth = command.getStrokeWidth();
			if(strokeWidth > 0 && command.getNumberOfPoints() > 1) {
				for(int i = 0; i < points.size(); i++) {
					if(i != 0) {
						Point last = getNearestGridDisplayPoint(points.get(i - 1));
						Point next = getNearestGridDisplayPoint(points.get(i));
						g2d.drawLine(last.x, last.y, next.x, next.y);
					} else {
						// Draw just the first point
						Point next = getNearestGridDisplayPoint(points.get(i));
						g2d.fillOval(next.x, next.y, strokeWidth, strokeWidth);
					}
				}
			}
		} else {
			// Circle
			int radius = command.getPathOpenRadius();
			Point center = command.getPointArray().get(0);
			center = getNearestGridDisplayPoint(center);

			g2d.setColor(command.getFillColor());
			g2d.fillOval(center.x - (radius), center.y - (radius), 2 * radius, 2 * radius);
		}
		
		// Crosshair
		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(CROSSHAIR_WIDTH));
		g2d.drawLine(crossHair.x - CROSSHAIR_RADIUS, crossHair.y, crossHair.x + CROSSHAIR_RADIUS, crossHair.y);
		g2d.drawLine(crossHair.x, crossHair.y - CROSSHAIR_RADIUS, crossHair.x, crossHair.y + CROSSHAIR_RADIUS);
	}
	
	public void reset() {
		image = new PDCI(getSize());
		repaint();
		System.out.println("Canvas reset");
	}
	
	public void save(String path) {
		try {
			System.out.println("Saving...");
			image.writeToFile(path);
			System.out.println("Saved to " + path);
		} catch (Exception e) {
			System.err.println("Error writing file to " + path + ": " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	public void removeLastCommand() {
		System.out.println("Removing last command...");
		image.removeLastCommand();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Add point
		if(currentCommand == null) {
			// Begin command
			currentCommand = new PDC(gui.getType(), PDC.NOT_HIDDEN, gui.getStrokeColor(), gui.getStrokeWidth(), gui.getFillColor(), gui.getOpenPathRadius());
			System.out.println("Began new command");
		}
		
		// Add another point on path, or circle without a radius
		if((gui.getType() == PDC.TYPE_PATH) 
		|| (gui.getType() == PDC.TYPE_CIRCLE && currentCommand.getNumberOfPoints() < 1)) {
			currentCommand.addPoint(getNearestGridPoint(e.getPoint()));
		} else {
			System.out.println("Circle can only have one radius");
		}
		
		repaint();
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		if(currentCommand != null) {
			System.out.println("Mouse exited canvas, finalizing current command");

			// Stop the path, add the command
			image.addCommand(currentCommand);
			currentCommand = null;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// Update crosshairs
		crossHair = getNearestGridDisplayPoint(getNearestGridPoint(e.getPoint()));
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) { }


}
