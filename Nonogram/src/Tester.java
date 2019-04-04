

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
		List<ArrayList<Integer>> puzzle = new ArrayList<>();
		
		ArrayList<Integer> puzzleRow1 = new ArrayList<>();
		ArrayList<Integer> puzzleRow2 = new ArrayList<>();
		ArrayList<Integer> puzzleRow3 = new ArrayList<>();
		ArrayList<Integer> puzzleRow4 = new ArrayList<>();
		ArrayList<Integer> puzzleRow5 = new ArrayList<>();
		
		puzzleRow1.add(0);
		puzzleRow1.add(0);
		puzzleRow1.add(1);
		puzzleRow1.add(0);
		puzzleRow1.add(0);
		
		puzzleRow2.add(0);
		puzzleRow2.add(0);
		puzzleRow2.add(0);
		puzzleRow2.add(0);
		puzzleRow2.add(0);
		
		puzzleRow3.add(1);
		puzzleRow3.add(0);
		puzzleRow3.add(1);
		puzzleRow3.add(0);
		puzzleRow3.add(1);
		
		puzzleRow4.add(0);
		puzzleRow4.add(0);
		puzzleRow4.add(0);
		puzzleRow4.add(0);
		puzzleRow4.add(0);
		
		puzzleRow5.add(0);
		puzzleRow5.add(0);
		puzzleRow5.add(1);
		puzzleRow5.add(0);
		puzzleRow5.add(0);
		
		puzzle.add(  puzzleRow1 );
		puzzle.add(  puzzleRow2 );
		puzzle.add(  puzzleRow3 );
		puzzle.add(  puzzleRow4 );
		puzzle.add(  puzzleRow5 );
		
		
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
		
		obj.getClues(methodRow, methodCol, (List<ArrayList<Integer>>)puzzle);
		
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
	

	
	@Test
	public void puzzleImageLoaderTest1() {
		PuzzleImageLoader test = new PuzzleImageLoader("C:\\Users\\Michael\\Pictures\\Nonogram Puzzles\\Puzzle1.png");
		//test.convertPic();
		ArrayList<ArrayList<Integer>> confirm = test.pOutput();
		
		//System.out.println(confirm.size());
		//System.out.println(confirm.get(0).get(2));
		assertTrue(confirm.get(0).get(2).equals(1));
	}
	
	@Test
	public void puzzleImageLoaderTest3() {
		PuzzleImageLoader test = new PuzzleImageLoader("C:\\Users\\Michael\\Pictures\\Nonogram Puzzles\\Puzzle3.png");
		ArrayList<ArrayList<Integer>> confirm = test.pOutput();
		
		System.out.println(confirm.size());
		
		for(int i=0; i<confirm.size(); i++) {
			for(int j=0; j<confirm.get(i).size(); j++) {
				System.out.print(confirm.get(i).get(j) + " ");
			}
			System.out.print("\n");
		}
		
		assertTrue(confirm.get(4).get(4).equals(1));
	}
	
	
	
	
	
	@Test
	public void testLoad() {
		Puzzle obj = new Puzzle(1, "admin");
		String temp1 = "010101010";
		String temp2 = "000000000";
		assertTrue(temp1.equals(obj.dataToString(obj.getMaster())));
		assertTrue(temp2.equals(obj.dataToString(obj.getWorking())));
	}
	


}
