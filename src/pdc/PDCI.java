package pdc;

import java.awt.Dimension;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * PDCI spec:
 * http://developer.getpebble.com/guides/pebble-apps/resources/pdc-format/#pebble-draw-command-image
 */
public class PDCI {
	
	private static final int VERSION = 1;
	private static final int MAX_BYTES = 5000;
	private static final String MAGIC_WORD = "PDCI";
	
	private Dimension viewBox;
	private ArrayList<PDC> commandList = new ArrayList<PDC>();
	
	/**
	 * Create a PebbleDrawCommandImage
	 * @param viewBox The size of the viewbox (entire image)
	 */
	public PDCI(Dimension viewBox) {
		this.viewBox = new Dimension(viewBox);
	}
	
	/**
	 * Add a command to the list of commands in this PebbleDrawCommandImage
	 * @param command The PDC to add.
	 */
	public void addCommand(PDC command) {
		commandList.add(command);
	}
	
	/**
	 * Remove the most recently added command.
	 */
	public void removeLastCommand() {
		if(commandList.size() > 0) {
			commandList.remove(commandList.size() - 1);
		} else {
			System.err.println("No commands to remove.");
		}
	}
	
	/**
	 * Get the size of this PDCI in bytes.
	 * @return The size of this PDCI in bytes.
	 */
	private int getSize() {
		int size = 0;
		
		size += 1;	// Version
		size += 1; 	// Reserved
		size += 4;	// Viewbox
		size += 2;	// List size
		
		// Draw command list
		for(PDC command : commandList) {
			size += command.getSize();
		}
		
		return size;
	}
	
	/**
	 * Serialize and write the entire PDCI to the specified path.
	 * @param path The path to write the PDCI to. Must end in '.pdc' to be compatible with Pebble.
	 * @return true if successful, false otherwise.
	 * @throws Exception Any exceptions that occur.
	 */
	public boolean writeToFile(String path) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(MAX_BYTES);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		// Magic word
		buffer.put((byte)'P');
		buffer.put((byte)'D');
		buffer.put((byte)'C');
		buffer.put((byte)'I');
		
		// Size
		buffer.putInt(getSize());
		
		// PDCI - Version
		buffer.put((byte)VERSION);
		
		// PDCI - Reserved
		buffer.put((byte)0);
		
		// PDCI - Viewbox
		buffer.putShort((short)viewBox.width);
		buffer.putShort((short)viewBox.height);
		
		// PDCL - Number of commands
		if(commandList.size() < 1) {
			throw new Exception("Number of commands must be larger than zero!");
		}
		buffer.putShort((short)commandList.size());
		
		// PDCL - The commands
		for(PDC command : commandList) {
			command.serialize(buffer);
		}
		
		int fileSize = buffer.position();
		
		// Finish and write to file
		File f = new File(path);
		DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(f));
		buffer.flip();
		while(buffer.position() < fileSize) {
			outputStream.write(buffer.get());
		}
		outputStream.flush();
		outputStream.close();
		return true;
	}
	
	/**
	 * Read a PDC file and construct this PDCI model object from its data.
	 * @param path The path to load the PDCI from.
	 * @return true if successful, false otherwise.
	 * @throws Exception Any exceptions thrown.
	 */
	public boolean readFromFile(String path) throws Exception {
		File f = new File(path);
		DataInputStream inputStream = new DataInputStream(new FileInputStream(f));
		
		// Read all bytes
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		while(inputStream.available() > 0) {
			ByteBuffer buffer = ByteBuffer.allocate(1);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.put((byte)inputStream.read());
			buffer.flip();
			bytes.add(buffer.get());
		}
		
		// Track position
		int ptr = 0;
		
		// Magic word
		String magicWord = new String();
		for(ptr = 0; ptr < 4; ptr++) {
			magicWord += (char)bytes.get(ptr).intValue();
		}
		if(!magicWord.equals(MAGIC_WORD)) {
			inputStream.close();
			throw new Exception("This is not a PDCI file!");
		}
	
		// Size - 4 bytes
		byte[] sizeBytes = new byte[4];
		for(; ptr < 8; ptr++) {
			sizeBytes[ptr - 4] = bytes.get(ptr);
		}
		int fileSize = sizeBytes[0];
		
		// Version
		byte versionByte = bytes.get(ptr);
		if(versionByte != VERSION) {
			inputStream.close();
			throw new Exception("PDC file is not compatible with the current version: " + VERSION);
		}
		
		// Reserved - byte 9
		ptr = 10;
		
		// Viewbox 2 x 2 butes
		byte[] viewXBytes = new byte[2];
		viewXBytes[0] = bytes.get(ptr++);
		viewXBytes[1] = bytes.get(ptr++);
		byte[] viewYBytes = new byte[2];
		viewYBytes[0] = bytes.get(ptr++);
		viewYBytes[1] = bytes.get(ptr++);
		viewBox = new Dimension(viewXBytes[0], viewYBytes[0]);
		
		// Number of commands - 2 bytes
		byte[] numCommandsBytes = new byte[2];
		numCommandsBytes[0] = bytes.get(ptr++);
		numCommandsBytes[1] = bytes.get(ptr++);
		int numCommands = numCommandsBytes[0];
		
		commandList = new ArrayList<PDC>(numCommands);
		
		// The commands
		for(int i = 0; i < numCommands; i++) {
			// Command metadata
			int type = bytes.get(ptr++);
			int hidden = bytes.get(ptr++);
			byte strokeColor = bytes.get(ptr++);
			int strokeWidth = bytes.get(ptr++);
			byte fillColor = bytes.get(ptr++);
			byte[] pathOpenRadiusBytes = new byte[2];
			pathOpenRadiusBytes[0] = bytes.get(ptr++);
			pathOpenRadiusBytes[1] = bytes.get(ptr++);
			int pathOpenRadius = pathOpenRadiusBytes[0];
			byte[] numPointsBytes = new byte[2];
			numPointsBytes[0] = bytes.get(ptr++);
			numPointsBytes[1] = bytes.get(ptr++);
			short numPoints = numPointsBytes[0];
			
			// Load points
			ArrayList<Point> points = new ArrayList<Point>();
			for(int j = 0; j < numPoints; j++) {
				byte[] xBytes = new byte[2];
				xBytes[0] = bytes.get(ptr++);
				xBytes[1] = bytes.get(ptr++);
				byte[] yBytes = new byte[2];
				yBytes[0] = bytes.get(ptr++);
				yBytes[1] = bytes.get(ptr++);
				points.add(new Point(xBytes[0], yBytes[0]));
			}
			
			// Add commands to image
			PDC pdc = new PDC(type, hidden, PebbleColor.fromPebbleColor(strokeColor), strokeWidth, PebbleColor.fromPebbleColor(fillColor), pathOpenRadius);
			for(int j = 0; j < points.size(); j++) {
				pdc.addPoint(points.get(j));
			}
			commandList.add(pdc);
		}

		inputStream.close();
		return true;
	}

	/**
	 * Get the list of commands in this DrawCommandImage.
	 * @return ArrayList of the PDC list.
	 */
	public ArrayList<PDC> getCommandList() {
		return commandList;
	}

}
