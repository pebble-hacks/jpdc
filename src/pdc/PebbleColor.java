package pdc;

import java.awt.Color;


public class PebbleColor {
	
	/**
	 * Convert a java.awt.Color to a Pebble Color byte representation (2-bit per argb)
	 * @param input The input Java Color
	 * @return A byte representing the color.
	 */
	public static byte fromColor(Color input) {
		// Get channels
        int r = input.getRed();
        int g = input.getGreen();
        int b = input.getBlue();

        // Convert palette
        r >>= 6;
        g >>= 6;
        b >>= 6;

        // Combine into byte
        byte result = (byte)0;
        result |= 3 << 6;   //Aplha
        result |= r << 4;
        result |= g << 2;
        result |= b;

        return result;
	}
	
	/**
	 * Construct a java.awt.Color object from a Pebble color byte.
	 * @param b The input Pebble Color byte representation (2-bit per argb)
	 * @return The Color constructed.
	 */
	public static Color fromPebbleColor(byte b) {
		// Byte to String of bits
		String s = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		
		// Get components (0 - 3)
		int red = Integer.parseInt(s.substring(2, 4), 2);
		int green = Integer.parseInt(s.substring(4, 6), 2);
		int blue = Integer.parseInt(s.substring(6, 8), 2);
		
		// Scale to 255
		red *= 85;
		green *= 85;
		blue *= 85;
		
		// Make approximate java AWT Color
		Color result = new Color(red, green, blue);
		return result;
	}
	
}
