package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pdc.PDC;

public class JPDCGUI {
	
	// TODO Cooler name
	private static final String APP_NAME = "JPDC";
	public static final Dimension WINDOW_SIZE = new Dimension(600, 550);
	
	private JFrame window;
	private PDCCanvas canvas;
	private JButton typeButton;
	private JCheckBox pathOpenCheckbox;
	private JTextField circleRadiusField;
	private JTextField strokeColorField;
	private JTextField strokeWidthField;
	private JTextField fillColorField;
	
	private int currentType = PDC.TYPE_PATH;
	
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
		
		canvas = new PDCCanvas(this);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 5;
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
				canvas.repaint();
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
				
				canvas.repaint();
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
				JFileChooser chooser = new JFileChooser();
				int val = chooser.showSaveDialog(window);
				if(val == JFileChooser.APPROVE_OPTION) {
					canvas.save(chooser.getSelectedFile().getAbsolutePath());
				}
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
		
		typeButton = new JButton("Type: Path");
		typeButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Toggle type
				if(currentType == PDC.TYPE_PATH) {
					currentType = PDC.TYPE_CIRCLE;
					typeButton.setText("Type: Circle");
				} else {
					currentType = PDC.TYPE_PATH;
					typeButton.setText("Type: Path");
				}
			}
			
		});
		typeButton.setPreferredSize(standardSize);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2F;	
		gbc.weighty = 0.16F;
		gbl.setConstraints(typeButton, gbc);
		window.add(typeButton, gbc);
		
		JButton undoButton = new JButton("Undo");
		undoButton.addMouseListener(new SmallMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.removeLastCommand();
				canvas.repaint();
			}
			
		});
		undoButton.setPreferredSize(standardSize);
		gbc.gridx = 4;
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
		
		pathOpenCheckbox = new JCheckBox();
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
		
		circleRadiusField = new JTextField();
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
		
		strokeColorField = new JTextField();
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
		
		strokeWidthField = new JTextField();
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
		
		fillColorField = new JTextField();
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
		
		// Set default values
		pathOpenCheckbox.setSelected(true);
		circleRadiusField.setText("5");
		strokeColorField.setText("000000");
		strokeWidthField.setText("1");
		fillColorField.setText("FFFFFF");
		
		window.pack();
		window.setVisible(true);
	}
	
	public int getType() {
		return currentType;
	}

	public int getOpenPathRadius() {
		if(currentType == PDC.TYPE_PATH) {
			return pathOpenCheckbox.isSelected() ? PDC.PATH_OPEN : PDC.PATH_CLOSED;
		} else {
			return getCircleRadius();
		}
	}
	
	public int getCircleRadius() {
		try {
			return Integer.parseInt(circleRadiusField.getText());
		} catch(Exception e) {
			System.err.println("Parse of circle radius failed!");
			e.printStackTrace();
			return 0;
		}
	}
	
	public Color getStrokeColor() {
		try {
			String s = strokeColorField.getText();
			return stringToColor(s);
		} catch(Exception e) {
			System.err.println("Error parsing stroke color components!");
			e.printStackTrace();
			return Color.PINK;
		}
	}
	
	public int getStrokeWidth() {
		try {
			return Integer.parseInt(strokeWidthField.getText());
		} catch(Exception e) {
			System.err.println("Parse of stroke width failed!");
			e.printStackTrace();
			return 0;
		}
	}
	
	public Color stringToColor(String s) {
		int i = Integer.parseInt(s, 16);
		return new Color(i);
	}
	
	public Color getFillColor() {
		try {
			String s = fillColorField.getText();
			return stringToColor(s);
		} catch(Exception e) {
			System.err.println("Error parsing fill color components!");
			e.printStackTrace();
			return Color.PINK;
		}
	}

}
