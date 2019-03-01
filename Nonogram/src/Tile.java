import java.awt.Color;

/**
 * Tile is a wrapper to hold the information in one tile in a nonogram board.
 * Holds whether it is filled and the color stored within it
 * @author Stephen Ogden
 *
 */
public class Tile {
	
	private boolean xed;
	private boolean filled;
	private int color;
	
	/**
	 * default constructor.  Sets filled to false and color to black.
	 */
	public Tile() {
		filled = false;
		color = 0;
	}
	
	/**
	 * sets filled to f.
	 * @param f
	 */
	public Tile(boolean f) {
		filled = f;
		color = 0;
	}
	
	/**
	 * sets filled to false and color to c
	 * @param f boolean true if filled
	 * @param c rgb value to set tile color to
	 */
	public Tile(boolean f, int c) {
		filled = f;
		color = c;
	}
	
	/**
	 * sets filled to false and color to c
	 * @param f boolean true if filled
	 * @param c rgb value to set tile color to
	 */
	public Tile(boolean f, Color c) {
		filled = f;
		color = c.getRGB();
	}
	
	/**
	 * 
	 * @return true if the tile is filled
	 */
	public boolean isFilled() {
		return filled;
	}
	
	/**
	 * 
	 * @param f boolean true if filled
	 */
	public void setFilled(boolean f) {
		filled = f;
	}
	
	/**
	 * 
	 * @return the Color object stored in the tile
	 */
	public Color getColor() {
		return new Color(color);
	}
	
	/**
	 * 
	 * @return the Color object stored in the tile
	 */
	public int getColorRGB() {
		return color;
	}
	
	/**
	 * 
	 * @param c color to set the tile to
	 */
	public void setColor(Color c) {
		color = c.getRGB();
	}
	
	/**
	 * 
	 * @param c color to set the tile to
	 */
	public void setColor(int c) {
		color = c;
	}
	
	/**
	 * 
	 * @return true if there is an x in the tile
	 */
	public boolean isXed() {
		return xed;
	}
	
	/**
	 * 
	 * @param x boolean true if there is an x in the tile
	 */
	public void setXed(boolean x) {
		xed = x;
	}
}
