import java.util.Random;
public class Randomizer {

	
	//simply contains the method randomizer, or more so the logic for it	
	//@author Michael Walker
	public static Tile[][] Randomizer(int x, int y) {
		//Initialize array/random variable
		Tile puzzle[][] = new Tile [x][y];
		Random r = new Random();
		Tile input = new Tile(false, 0);
		
		//For loop to fill array
		for(int i=0; i<x; i++) {
			for(int j=0; j<y; j++) {
				int x1 = r.nextInt(2); //make random variable
				
				
				if(x1==1) {
					input.setFilled(true);
					input.setColor(x1);
					puzzle[i][j] = input; 
				}
				else {
					input.setFilled(false);
					input.setColor(x1);
					puzzle[i][j] = input; 
				}
				
				
			}
		}
		
		
		
		
		return puzzle; //returns puzzle
	}
	
	
	//This is for testing purposes
	//Outputs the puzzle made
	public static void main(String[] args) {
		
		/**
		int x = 5; //var1
		int y = 5; //var2
		int puzzle[][]; //puzzle
		puzzle = Randomizer(x, y); //load
		
		//For loop for output
		for(int i=0; i<x; i++) {
			for(int j=0; j<y; j++) {
				System.out.print(puzzle[i][j] + " ");
			}
		 System.out.print("\n");
		}
		*/
	}
	
	
	
	
	
	
	
}
