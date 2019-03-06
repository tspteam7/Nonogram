import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class TileTest {
	
	Tile tile;
	
	@Before
	public void setup() {
		tile = new Tile();
	}

	@Test
	public void constructor1Test() {
		tile = new Tile();
		assertTrue(!tile.isFilled() && tile.getColorRGB() == 0);
	}

	@Test
	public void constructor2Test() {
		tile = new Tile(true);
		assertTrue(tile.isFilled() && tile.getColorRGB() == 0);
	}
	
	@Test
	public void constructor3Test() {
		tile = new Tile(true, 50);
		assertTrue(tile.isFilled() && tile.getColorRGB() == 50);
	}
	
	@Test
	public void constructor4Test() {
		tile = new Tile(true, Color.blue);
		assertTrue(tile.isFilled() && tile.getColor().equals(Color.blue));
	}
	
	@Test
	public void setFilledTest() {
		tile.setFilled(true);;
		assertTrue(tile.isFilled());
	}
	
	@Test
	public void setColorTest() {
		tile.setColor(Color.blue);
		assertTrue(tile.getColor().equals(Color.blue));
	}
	
	@Test
	public void setColorRGBTest() {
		tile.setColor(35);
		assertTrue(tile.getColorRGB() == 35);
	}
	
	@Test
	public void setXedTest() {
		tile.setXed(true);
		assertTrue(tile.isXed());
	}
}
