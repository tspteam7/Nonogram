import java.util.Random;
import java.util.*;
public class Randomizer {
	
	
	/**
	 * Method that takes in two integers and returns a 2D arraylist
	 * with dimensions of x, y.  The values of the array are
	 * randomly assigned to be 0 or 1.
	 * 
	 * @param x integer input for the desired number of rows
	 * @param y integer input for the desired number of columns
	 * @return puzzle a list of x lists that are y long 
	 * @author Michael Walker
	 * 
	 */		
	public ArrayList<ArrayList<Integer>> randomizer(int x, int y) {
		//Initialize array/random variable
		ArrayList<ArrayList<Integer>> puzzle = new ArrayList<ArrayList<Integer>>();		
		Random r = new Random();
		
		//For loop to fill list
		for(int i=0; i<x; i++) {
			ArrayList<Integer> inner = new ArrayList<Integer>(); //creates inner list
			for(int j=0; j<y; j++) {
				int x1 = r.nextInt(2); //make random variable				
				inner.add(x1);
			}
			puzzle.add(inner);
		}		
		return puzzle; //returns puzzle
	}
}
