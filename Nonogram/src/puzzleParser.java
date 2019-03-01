
import java.util.*;

public class puzzleParser {
	
	
	public puzzleParser() {
		
	}
	
	public void parseRowsAndColumns(List<ArrayList<Integer>> methodRow, List<ArrayList<Integer>> methodCol, int[][] puzzle ) {
		int counter = 0;
		
		// Iterates through the rows 
		for( int i = 0; i < puzzle.length; i++ ) {
			for( int j = 0; j < puzzle[i].length; j++ ) {
				if( puzzle[i][j] > 0 ) {
					counter++;
				} else { // If the tile is not filled...
					if( counter != 0 ) { // If the tile is not filled and there has been a filled tile previously
						methodRow.get(i).add(counter);
						counter = 0;
					}
				}
			}
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
						methodCol.get(j).add(counter);
						counter = 0; // because there was a break in the number of filled spaces the counter is reset
					}
				}
			}
			j++; // Increments j to access the next column to be read
			counter = 0; // resets the counter to start again
		}
	}
}