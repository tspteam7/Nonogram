
import java.util.*;

public class puzzleParser {	
	
	public puzzleParser() {
		
	}
	
	
	
	/**
	 * This method iterates through the rows of the given puzzle,
	 * finding the number of 1's in the row and if they are next to each other.
	 * For example say you have this puzzle
	 * 					puzzle = { {0,0,1,0,0},
	 * 							   {0,0,1,0,0},
	 * 							   {1,1,0,1,1},
	 * 							   {0,0,1,0,0},
	 * 							   {0,0,1,0,0} }
	 * Then the row list would contain { {1}, {1}, {2,2}, {1}, {1} }
	 * because the first row only has one '1' in it, while the third row has 4 '1's
	 * and they are grouped into pairs.
	 * The process is reiterated to go by column.
	 * 
	 * @param row list containing the result of iterating through the rows
	 * @param col list containing the result of iterating through the columns
	 * @param puzzle input that is going to be read.
	 * @author Luke Crandall
	 * 
	 */
	public void getClues(List<ArrayList<Integer>> row, List<ArrayList<Integer>> col, List<ArrayList<Integer>> puzzle ) {
		int counter = 0;
		ArrayList<Integer> temp = new ArrayList<>();
		
		// Iterates through the rows 
		for( int i = 0; i < puzzle.size(); i++ ) {
			for( int j = 0; j < puzzle.get(i).size(); j++ ) {
				if( puzzle.get(i).get(j) > 0 ) {
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
			row.add(temp);
			temp = new ArrayList<>();
			counter = 0; // Resets the counter to be used again
		}
		
		// Iterates through the columns
		int j = 0;
		while( j < puzzle.get(0).size() ) {
			for( int i = 0; i < puzzle.size(); i++ ) {
				if( puzzle.get(i).get(j) > 0 ) { // If the tile is filled then the counter is incremented
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
			col.add(temp);
			temp = new ArrayList<>();
			counter = 0; // resets the counter to start again
		}
	}
}