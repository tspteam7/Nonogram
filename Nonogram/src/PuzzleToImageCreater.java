import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class PuzzleToImageCreater {
	
	public BufferedImage img = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);;
	ArrayList<ArrayList<Integer>> currentPuzzle = new ArrayList<ArrayList<Integer>>();
	
	PuzzleToImageCreater(ArrayList<ArrayList<Integer>> loaded){
		img = new BufferedImage(currentPuzzle.get(0).size(), currentPuzzle.size(), BufferedImage.TYPE_INT_RGB);
		currentPuzzle = loaded;		
		convert();
	}
	
	private void convert() {
		for(int i=0; i<100; i++) {
			for(int j=0; j<100; j++) {
				if(currentPuzzle.get(i).get(j) == 1) {
					img.setRGB(j, i, 0xFF);
				}else {
					img.setRGB(j, i, 0);
				}
				//0 and 2 are white	
			}	
		}
		img = (BufferedImage) img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
	}
	
	BufferedImage shoot() {
		return img;
	}
}