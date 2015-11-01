package core;

import gui.GUI;

public class JPDCMain {
	
	public static void main(String[] args) {
		// Usage
		System.out.println("Click canvas to add a point to a Path, or set radius of a circle.");
		System.out.println("Exiting the canvas will END the current command. This means you should set the bottom parameters and type before clicking the canvas.");
		System.out.println("Use the Undo button to remove the most recent command");
		
		new GUI();
	}
	
}
