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
	 * @param pathOpenRadius For path type, one of PATH_OPEN or PATH_CLOSED. For circle type, the radius of the circle.
	 */
	public PDC(int type, int hidden, Color strokeColor, int strokeWidth, Color fillColor, int pathOpenRadius) {
		this.type = (byte)type;
		flags = (byte)hidden;
		this.strokeWidth = (byte)strokeWidth;
		this.strokeColor = PebbleColor.fromColor(strokeColor);
		this.fillColor = PebbleColor.fromColor(fillColor);
		this.pathOpenRadius = (byte)pathOpenRadius;
	}
	
	/**
	 * Add a point to this command's point list
	 * @param point The point to add.
	 * @return this, for chaining of addPoint() calls.
	 */
	public PDC addPoint(Point point) {
		pointArray.add(point);
		return this;
	}
	
	/**
	 * Serialize this PDC into bytes.
	 * @param buffer The ByteBuffer provided by the PDCI when saving to disk.
	 */
	public void serialize(ByteBuffer buffer) {
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
	
	/**
	 * Calculate the size of this command.
	 * @return The size of this command in bytes.
	 */
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
	
	/**
	 * Get the number of points in this command/
	 * @return The size of the point array.
	 */
	public int getNumberOfPoints() {
		return pointArray.size();
	}

	/**
	 * Get the type of this command. One of TYPE_PATH or TYPE_CIRCLE.
	 * @return The type of this command as a byte.
	 */
	public byte getType() {
		return type;
	}

	/**
	 * Get the java.awt.Color of this command's stroke color for preview purposes.
	 * @return java.awt.Color of this command's stroke color.
	 */
	public Color getStrokeColor() {
		return PebbleColor.fromPebbleColor(strokeColor);
	}

	/**
	 * Get the stroke width of this command for preview purposes.
	 * @return The stroke width.
	 */
	public int getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * Get the java.awt.Color of this command's fill color for preview purposes.
	 * @return java.awt.Color of this command's fill color.
	 */
	public Color getFillColor() {
		return PebbleColor.fromPebbleColor(fillColor);
	}

	/**
	 * Get the value of the 'pathOpenRadius' field. For path type, a byte of either PATH_OPEN or PATH_CLOSED, padded by an extra byte.
	 * For circle type, the radius of the circle as a two-byte short.
	 * @return The pathOpenRadius value, as descirbed above.
	 */
	public byte getPathOpenRadius() {
		return pathOpenRadius;
	}

	/**
	 * Get the list of points.
	 * @return The ArrayList containing all the points in this command.
	 */
	public ArrayList<Point> getPointArray() {
		return pointArray;
	}
	
}
