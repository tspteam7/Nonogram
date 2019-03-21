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
	public List<List<Tile>> randomizer(int x, int y) {
		//Initialize array/random variable
		List<List<Tile>> puzzle = new ArrayList<List<Tile>>();		
		
		
		Random r = new Random();
		Tile input = new Tile(false, 0);
		
		//For loop to fill list
		for(int i=0; i<x; i++) {
			List<Tile> inner = new ArrayList<Tile>(); //creates inner list
			for(int j=0; j<y; j++) {
				int x1 = r.nextInt(2); //make random variable				
				if(x1==1) {
					input.setFilled(true);
					input.setColor(x1);
					inner.add(input);
					
				}
				else {
					input.setFilled(false);
					input.setColor(x1);
					inner.add(input); 
					
				}
			}
			puzzle.add(inner);
			//System.out.println("\n");
		}		
		return puzzle; //returns puzzle
	}
}
