

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class Tester {
	

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
	
	@Test
	public void testParse() {
		int[][] testPuzzle = {
				{0,0,1,0,0},
				{0,0,0,0,0},
				{1,0,1,0,1},
				{0,0,0,0,0},
				{0,0,1,0,0}
		};
		List<ArrayList<Integer>> testRow = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> testCol = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row1 = new ArrayList<Integer>();
		ArrayList<Integer> row2 = new ArrayList<Integer>();
		ArrayList<Integer> row3 = new ArrayList<Integer>();
		ArrayList<Integer> row4 = new ArrayList<Integer>();
		ArrayList<Integer> row5 = new ArrayList<Integer>();
		
		row1.add(1);
		row3.add(1);
		row3.add(1);
		row3.add(1);
		row5.add(1);
		
		ArrayList<Integer> col1 = new ArrayList<Integer>();
		ArrayList<Integer> col2 = new ArrayList<Integer>();
		ArrayList<Integer> col3 = new ArrayList<Integer>();
		ArrayList<Integer> col4 = new ArrayList<Integer>();
		ArrayList<Integer> col5 = new ArrayList<Integer>();
		
		col1.add(1);
		col3.add(1);
		col3.add(1);
		col3.add(1);
		col5.add(1);
		
		testRow.add(row1);
		testRow.add(row2);
		testRow.add(row3);
		testRow.add(row4);
		testRow.add(row5);
		
		testCol.add(col1);
		testCol.add(col2);
		testCol.add(col3);
		testCol.add(col4);
		testCol.add(col5);
		
		
		List<ArrayList<Integer>> methodRow = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> methodCol = new ArrayList<ArrayList<Integer>>();
		
		puzzleParser obj = new puzzleParser();
		
		obj.getClues(methodRow, methodCol, testPuzzle);
		
		for( int i = 0; i < methodRow.size(); i++ ) {
			for( int j = 0; j < methodRow.get(i).size(); j++ ) {
				assertEquals( testRow.get(i).get(j), methodRow.get(i).get(j) );
			}
		}
		
		for( int i = 0; i < methodCol.size(); i++ ) {
			for( int j = 0; j < methodCol.get(i).size(); j++ ) {
				assertEquals( testCol.get(i).get(j), methodCol.get(i).get(j) );
			}
		}
		
		
		
	}
	
	@Test
	public void testRandomizer() {
		Randomizer obj = new Randomizer();
		int x = 5;
		int y = 5;
		
		ArrayList<ArrayList<Integer>> puzzle = new ArrayList<>();
		
		puzzle = obj.randomizer(x, y);
		
		int counterX = 0;
		for( int i = 0; i < puzzle.size(); i++) { //Test the x value is the number of lists
			counterX++;
		}
		
		int counterY = 0;
		for( int i = 0; i < puzzle.get(0).size(); i++ ) { //Test that y value is the size of the lists
			counterY++;
		}
		
		assertEquals(y, counterY);
		assertEquals(x, counterX);
		
		
	}
	
	//Puzzle class tests
	@Test
	public void testStringToArraylist() {
		Puzzle obj = new Puzzle();
		ArrayList<ArrayList<Integer>> al = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			al.add(new ArrayList<Integer>());
			for(int j = 0; j < 6; j++) {
				al.get(i).add(j, j);
			}
		}
		
		assertTrue(al.equals(obj.stringToData("012345012345012345012345012345", 6, 5)));
		
	}
	
	@Test
	public void testArraylistToString() {
		Puzzle obj = new Puzzle();
		ArrayList<ArrayList<Integer>> al = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			al.add(new ArrayList<Integer>());
			for(int j = 0; j < 6; j++) {
				al.get(i).add(j, j);
			}
		}

		assertTrue("012345012345012345012345012345".equals(obj.dataToString(al)));
		
	}
	

}
