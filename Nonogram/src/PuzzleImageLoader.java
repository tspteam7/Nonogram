import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class PuzzleImageLoader {
	public BufferedImage img = null;
	ArrayList<ArrayList<Integer>> currentPuzzle = new ArrayList<ArrayList<Integer>>();
	
	PuzzleImageLoader(String address) {
		try {
			img = ImageIO.read(new File(address));	
		//Format :
		//"C:\\Documents and settings\\<username>\\My Documents\\images.jpeg"
			convertPic();
		}catch(IOException e) {}
	}
	
	public void convertPic() {
		currentPuzzle = new ArrayList<ArrayList<Integer>>();
		int width = img.getWidth();
		int height = img.getHeight();
		
		
		for(int i=0;i<height;i++) {
			ArrayList<Integer> inner = new ArrayList<Integer>();
			for(int j=0; j<width;j++) {
				Color p = new Color (img.getRGB(j,  i));
				int r = p.getRed();
				int g = p.getGreen();
				int b = p.getBlue();
				if(r>0 && g>0 && b>0){
					inner.add(0);
				}else {
					inner.add(1);
				}
			
			}
			
			currentPuzzle.add(inner);
		}
	}
	
	public ArrayList<ArrayList<Integer>> pOutput() {
		return currentPuzzle;
	}
	
	
	
}
