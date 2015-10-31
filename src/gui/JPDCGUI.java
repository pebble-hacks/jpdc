package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JPDCGUI {
	
	// TODO Cooler name
	private static final String APP_NAME = "JPDC";
	public static final Dimension WINDOW_SIZE = new Dimension(600, 400);
	
	private JFrame window;
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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		window.setLayout(gbl);
		
		gbc.anchor = GridBagConstraints.WEST;
		Dimension standardSize = new Dimension(100, 30);
		
		canvas = new PDCCanvas();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 6;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.8F;
		gbc.weighty = 0.9F;
		gbl.addLayoutComponent(canvas, gbc);
		window.add(canvas, gbc);
		
		
		JButton newButton = new JButton("New");
		newButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Wipe the list of drawn commands
				canvas.reset();
			}
			
		});
		newButton.setPreferredSize(standardSize);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(newButton, gbc);
		window.add(newButton, gbc);
		
		JButton openButton = new JButton("Open");
		openButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Load a PDC file (open dialog)
			}
			
		});
		openButton.setPreferredSize(standardSize);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(openButton, gbc);
		window.add(openButton, gbc);
		
		JButton saveButton = new JButton("Save");
		saveButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Save the PDC file (open dialog)
			}
			
		});
		saveButton.setPreferredSize(standardSize);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(saveButton, gbc);
		window.add(saveButton, gbc);
		
		JButton pathButton = new JButton("Add path");
		pathButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO prepare canvas to receive clicks
				
			}
			
		});
		pathButton.setPreferredSize(standardSize);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(pathButton, gbc);
		window.add(pathButton, gbc);
		
		JButton circleButton = new JButton("Add circle");
		circleButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Prompt for circle radius, then prompt to click the center point
				
			}
			
		});
		circleButton.setPreferredSize(standardSize);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(circleButton, gbc);
		window.add(circleButton, gbc);
		
		JButton undoButton = new JButton("Undo");
		undoButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Remove the last command
				
			}
			
		});
		undoButton.setPreferredSize(standardSize);
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(undoButton, gbc);
		window.add(undoButton, gbc);
		
		JLabel pathOpenLabel = new JLabel("pathOpen");
		pathOpenLabel.setPreferredSize(standardSize);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.4F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(pathOpenLabel, gbc);
		window.add(pathOpenLabel, gbc);
		
		JLabel circleRadiusLabel = new JLabel("Circle Radius");
		circleRadiusLabel.setPreferredSize(standardSize);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(circleRadiusLabel, gbc);
		window.add(circleRadiusLabel, gbc);
		
		JLabel strokeColorLabel = new JLabel("Stroke Color");
		strokeColorLabel.setPreferredSize(standardSize);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(strokeColorLabel, gbc);
		window.add(strokeColorLabel, gbc);
		
		JLabel strokeWidthLabel = new JLabel("Stroke Width");
		strokeWidthLabel.setPreferredSize(standardSize);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(strokeWidthLabel, gbc);
		window.add(strokeWidthLabel, gbc);
		
		JLabel fillColorLabel = new JLabel("Fill Color");
		fillColorLabel.setPreferredSize(standardSize);
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(fillColorLabel, gbc);
		window.add(fillColorLabel, gbc);
		
		JCheckBox pathOpenCheckbox = new JCheckBox();
		pathOpenCheckbox.setPreferredSize(standardSize);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(pathOpenCheckbox, gbc);
		window.add(pathOpenCheckbox, gbc);
		
		JTextField circleRadiusField = new JTextField();
		circleRadiusField.setPreferredSize(standardSize);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(circleRadiusField, gbc);
		window.add(circleRadiusField, gbc);
		
		JTextField strokeColorField = new JTextField();
		strokeColorField.setPreferredSize(standardSize);
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(strokeColorField, gbc);
		window.add(strokeColorField, gbc);
		
		JTextField strokeWidthField = new JTextField();
		strokeWidthField.setPreferredSize(standardSize);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(strokeWidthField, gbc);
		window.add(strokeWidthField, gbc);
		
		JTextField fillColorField = new JTextField();
		fillColorField.setPreferredSize(standardSize);
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(fillColorField, gbc);
		window.add(fillColorField, gbc);
		
		window.pack();
		window.setVisible(true);
	}

}
