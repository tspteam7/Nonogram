
import java.util.*;

public class puzzleParser {
	
	public static void main( String[] args ) {
		int[][] testPuzzle = {
				{0,0,1,0,0},
				{0,0,0,0,0},
				{1,0,1,0,1},
				{0,0,0,0,0},
				{0,0,1,0,0}
		};
		
		List<ArrayList<Integer>> row = new ArrayList<>();
		List<ArrayList<Integer>> col = new ArrayList<>();
		
		puzzleParser obj = new puzzleParser();
		
		obj.getClues( row, col, testPuzzle );
		
		
		System.out.println("Rows:");
		for( int i =0 ; i < row.size(); i++ ) {
			for( int j = 0; j < row.get(i).size(); j++ ) {
				System.out.print(row.get(i).get(j));
			}
			System.out.println();
		}
		System.out.println("Columns:");
		for( int i =0 ; i < col.size(); i++ ) {
			for( int j = 0; j < col.get(i).size(); j++ ) {
				System.out.print(col.get(i).get(j));
			}
			System.out.println();
		}
		
		
		for( int i = 0; i < testPuzzle.length; i++ ) {
			for( int j = 0; j < testPuzzle[i].length; j++ ) {
				System.out.print(testPuzzle[i][j]);
			}
			System.out.println();
		}
	}
	
	
	public puzzleParser() {
		
	}
	
	public void getClues(List<ArrayList<Integer>> methodRow, List<ArrayList<Integer>> methodCol, int[][] puzzle ) {
		int counter = 0;
		ArrayList<Integer> temp = new ArrayList<>();
		
		// Iterates through the rows 
		for( int i = 0; i < puzzle.length; i++ ) {
			for( int j = 0; j < puzzle[i].length; j++ ) {
				if( puzzle[i][j] > 0 ) {
					counter++;
				} else { // If the tile is not filled...
					if( counter != 0 ) { // If the tile is not filled and there has been a filled tile previously
						temp.add(counter);
						counter = 0;
					}
				}
			}
			if( counter != 0 ) {
				temp.add(counter);
			}
			methodRow.add(temp);
			temp = new ArrayList<>();
			counter = 0; // Resets the counter to be used again
		}
		
		// Iterates through the columns
		int j = 0;
		while( j < puzzle[0].length ) {
			for( int i = 0; i < puzzle.length; i++ ) {
				if( puzzle[i][j] > 0 ) { // If the tile is filled then the counter is incremented
					counter++;
				} else { // Else the value of the counter is added to a list 
					if( counter != 0 ) { // as long as there is a valid value in the counter
						temp.add(counter);
						counter = 0; // because there was a break in the number of filled spaces the counter is reset
					}
				}
			}
			j++; // Increments j to access the next column to be read
			if( counter != 0 ) {
				temp.add(counter);
			}
			methodCol.add(temp);
			temp = new ArrayList<>();
			counter = 0; // resets the counter to start again
		}
	}
}