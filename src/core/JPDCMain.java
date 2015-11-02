package core;

import gui.GUI;

public class JPDCMain {
	
	/**
	 * 0.1 - Initial release.
	 */
	private static final String VERSION = "0.1";
	
	public static void main(String[] args) throws Exception {
		System.out.println("JPDC version " + VERSION);
		System.out.println();
		
		// Usage info
		System.out.println("Click canvas to add a point to a Path, or set radius of a circle.");
		System.out.println("Exiting the canvas will END the current command.");
		System.out.println("This means you should set the bottom parameters and type before clicking the canvas.");
		System.out.println("Use the Undo button to remove the most recent command");
		
		new GUI();
	}
	
}
