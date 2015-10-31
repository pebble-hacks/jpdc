package core;

import gui.JPDCGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import pdc.PDC;
import pdc.PDCI;

public class JPDCMain {
	
	// TODO JSON interface

	public static void main(String[] args) {
		test();
		new JPDCGUI();
	}
	
	public static void test() {
		Dimension viewBox = new Dimension(30, 30);

		// Create the PebbleDrawCommandImage
		PDCI pdci = new PDCI(viewBox);
		
		// Create a PebbleDrawCommand (square box)
		PDC squareCommand = new PDC(PDC.TYPE_PATH, PDC.NOT_HIDDEN, Color.BLACK, 3, Color.WHITE, PDC.PATH_CLOSED)
			.addPoint(new Point(5, 5))
			.addPoint(new Point(25, 5))
			.addPoint(new Point(25, 25))
			.addPoint(new Point(5, 25));
		
		// Add the command to the image command list
		pdci.addCommand(squareCommand);
		
		// Create a circle command
		PDC circleCommand = new PDC(PDC.TYPE_CIRCLE, PDC.NOT_HIDDEN, Color.BLACK, 3, Color.WHITE, 10)
			.addPoint(new Point(5, 5));
		pdci.addCommand(circleCommand);
		
		try {
			// Write to file
			pdci.writeToFile("./output.pdc");
			
			// Read back
//			PDCI readImage = new PDCI(viewBox);
//			readImage.readFromFile("./output.pdc");
		} catch (Exception e) {
			System.err.println("Error saving PDC: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
