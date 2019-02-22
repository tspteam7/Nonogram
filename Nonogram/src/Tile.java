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
	private Color color;
	
	/**
	 * default constructor.  Sets filled to false and color to black.
	 */
	public Tile() {
		filled = false;
		color = Color.black;
	}
	
	/**
	 * sets filled to f.
	 * @param f
	 */
	public Tile(boolean f) {
		filled = f;
		color = Color.black;
	}
	
	/**
	 * sets filled to false and color to c
	 * @param f boolean true if filled
	 * @param c color to set tile to
	 */
	public Tile(boolean f, Color c) {
		filled = f;
		color = c;
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
	 * @return the color of the tile
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * 
	 * @param c color to set the tile to
	 */
	public void setColor(Color c) {
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
