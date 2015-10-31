package pdc;

import java.awt.Color;
import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PDC {
	
	// TODO Implement precise path
	public static final int 
		TYPE_PATH = 1,
		TYPE_CIRCLE = 2;
	
	public static final byte
		PATH_OPEN = (byte)1,
		PATH_CLOSED = (byte)0,
		HIDDEN = (byte)1,
		NOT_HIDDEN = (byte)0;
	
	// Spec values
	private byte type, flags, strokeColor, strokeWidth, fillColor, pathOpenRadius;
	private ArrayList<Point> pointArray = new ArrayList<Point>();
	
	/**
	 * Create a PebbleDrawCommand
	 * @param type Type of this command. One of TYPE_PATH or TYPE_CIRCLE.
	 * @param hidden Whether this command will be hidden (not drawn). One of HIDDEN or NOT_HIDDEN.
	 * @param strokeColor java.awt.Color of the stroke. Will be downshifted to a Pebble color (8bit argb).
	 * @param strokeWidth Stroke width.
	 * @param fillColor java.awt.Color of the fill if a closed path or circle.
	 * @param pathOpenRadius For path type, one of PATH_OPEN or PATH_CLOSED. For cicle type, the radius of the circle.
	 */
	public PDC(int type, int hidden, Color strokeColor, int strokeWidth, Color fillColor, int pathOpenRadius) {
		this.type = (byte)type;
		flags = (byte)hidden;
		this.strokeWidth = (byte)strokeWidth;
		this.strokeColor = PebbleColorConverter.fromColor(strokeColor);
		this.fillColor = PebbleColorConverter.fromColor(fillColor);
		this.pathOpenRadius = (byte)pathOpenRadius;
	}
	
	public PDC addPoint(Point point) {
		pointArray.add(point);
		return this;
	}
	
	public void serialize(ByteBuffer buffer) throws Exception {
		buffer.put(type);
		buffer.put(flags);
		buffer.put(strokeColor);
		buffer.put(strokeWidth);
		buffer.put(fillColor);
		buffer.putShort(pathOpenRadius);
		buffer.putShort((short)pointArray.size());
		for(Point point : pointArray) {
			buffer.putShort((short)point.x);
			buffer.putShort((short)point.y);
		}
	}
	
	public int getSize() {
		int size = 1	// Type
				 + 1	// Flags
				 + 1	// Stroke color
				 + 1	// Stroke width
				 + 1	// Fill color
				 + 2	// Path open/radius
				 + 2	// Number of points
				 + 4 * pointArray.size();
		return size;
	}
	
	public int getNumberOfPoints() {
		return pointArray.size();
	}

	public byte getType() {
		return type;
	}

	public Color getStrokeColor() {
		return PebbleColorConverter.fromPebbleColor(strokeColor);
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public Color getFillColor() {
		return PebbleColorConverter.fromPebbleColor(fillColor);
	}

	public byte getPathOpenRadius() {
		return pathOpenRadius;
	}

	public ArrayList<Point> getPointArray() {
		return pointArray;
	}
	
}
