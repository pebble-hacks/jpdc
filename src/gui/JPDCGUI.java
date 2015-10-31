package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JPDCGUI {
	
	// TODO Cooler name
	private static final String APP_NAME = "JPDC";
	public static final Dimension WINDOW_SIZE = new Dimension(240, 240);
	
	private JFrame window;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private JLabel statusLabel;
	private PDCCanvas canvas;
	
	public JPDCGUI() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				createUI();
			}
			
		});
	}
	
	public void createUI() {
		window = new JFrame(APP_NAME);
		window.setPreferredSize(WINDOW_SIZE);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		window.setLayout(gbl);
		
		gbc.anchor = GridBagConstraints.WEST;
		
		canvas = new PDCCanvas();
		setAllConstraints(0, 0, 1, 5);
		gbc.weightx = 0.8F;
		gbl.addLayoutComponent(canvas, gbc);
		window.add(canvas, gbc);
		
		statusLabel = new JLabel("Ready");
		statusLabel.setPreferredSize(new Dimension(WINDOW_SIZE.width, 30));
		statusLabel.setBackground(Color.LIGHT_GRAY);
		setAllConstraints(0, 6, 2, 1);
		gbl.addLayoutComponent(statusLabel, gbc);
		window.add(statusLabel, gbc);
		
		Dimension buttonDimension = new Dimension(40, 20);
		
		JButton newButton = new JButton("New");
		newButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Wipe the list of drawn commands
				canvas.reset();
			}
			
		});
		newButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 0, 1, 1);
		gbl.setConstraints(newButton, gbc);
		window.add(newButton, gbc);
		
		JButton openButton = new JButton("Open");
		openButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Load a PDC file (open dialog)
			}
			
		});
		openButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 1, 1, 1);
		gbl.setConstraints(openButton, gbc);
		window.add(openButton, gbc);
		
		JButton saveButton = new JButton("Save");
		saveButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Save the PDC file (open dialog)
			}
			
		});
		saveButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 2, 1, 1);
		gbl.setConstraints(saveButton, gbc);
		window.add(saveButton, gbc);
		
		JButton pathButton = new JButton("Add path");
		pathButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				statusLabel.setText("Click points on desired path");
				
				// TODO prepare canvas to receive clicks
			}
			
		});
		pathButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 3, 1, 1);
		gbl.setConstraints(pathButton, gbc);
		window.add(pathButton, gbc);
		
		JButton circleButton = new JButton("Add circle");
		circleButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Prompt for circle radius, then prompt to click the center point
				
			}
			
		});
		circleButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 4, 1, 1);
		gbl.setConstraints(circleButton, gbc);
		window.add(circleButton, gbc);
		
		JButton undoButton = new JButton("New");
		undoButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Remove the last command
				
			}
			
		});
		undoButton.setPreferredSize(buttonDimension);
		setAllConstraints(1, 5, 1, 1);
		gbl.setConstraints(undoButton, gbc);
		window.add(undoButton, gbc);
		
		window.pack();
		window.setVisible(true);
	}
	
	private void setAllConstraints(int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		gbc.gridheight = h;
		gbc.weightx = 0.2F;
	}

}
