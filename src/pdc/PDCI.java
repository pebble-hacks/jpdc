package pdc;

import java.awt.Dimension;
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
		int pos = 0;
		
		// Magic word
		String magicWord = new String();
		for(pos = 0; pos < 4; pos++) {
			magicWord += (char)bytes.get(pos).intValue();
		}
		if(!magicWord.equals(MAGIC_WORD)) {
			inputStream.close();
			throw new Exception("This is not a PDCI file!");
		}
	
		// Size
		byte[] size = new byte[4];
		for(; pos < 8; pos++) {
			size[pos - 4] = bytes.get(pos);
		}
		int fileSize = size[0];
		
		// Version
		byte version = bytes.get(8);
		if(version != VERSION) {
			inputStream.close();
			throw new Exception("PDC file is not compatible with the current version: " + VERSION);
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
