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
	
	private Dimension viewBox;
	private ArrayList<PDC> commandList = new ArrayList<PDC>();
	
	/**
	 * Create a PebbleDrawCommandImage
	 * @param viewBox The size of the viewbox (entire image)
	 */
	public PDCI(Dimension viewBox) {
		this.viewBox = new Dimension(viewBox);
	}
	
	public void addCommand(PDC command) {
		commandList.add(command);
	}
	
	public void removeLastCommand() {
		if(commandList.size() > 0) {
			commandList.remove(commandList.size() - 1);
		} else {
			System.err.println("No commands to remove.");
		}
	}
	
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
	
	public boolean readFromFile(String path) throws Exception {
		File f = new File(path);
		
		// TODO Read as little endian - difficult in Java
		
		return true;
	}

	public ArrayList<PDC> getCommandList() {
		return commandList;
	}

}
