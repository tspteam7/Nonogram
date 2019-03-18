

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class parseTester {
	
	
	
	@Test
	public void test() {
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

}
