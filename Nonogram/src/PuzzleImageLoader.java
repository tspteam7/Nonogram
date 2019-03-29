import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
public class PuzzleImageLoader {
	public BufferedImage img = null;
	ArrayList<ArrayList<Integer>> currentPuzzle = new ArrayList<ArrayList<Integer>>();
	
	PuzzleImageLoader(String address) {
		try {
			URL urlToImage = this.getClass().getResource(address);
			img = ImageIO.read(urlToImage);	
		//Format :
		//"C:\\Documents and settings\\<username>\\My Documents\\images.jpeg"
			convertPic();
		}catch(IOException e) {}
	}
	
	public void convertPic() {
		currentPuzzle = new ArrayList<ArrayList<Integer>>();
		int width = img.getWidth();
		int height = img.getHeight();
		ArrayList<Integer> inner = new ArrayList<Integer>();
		
		for(int i=0;i<height;i++) {
			for(int j=0; j<width;j++) {
				int p = img.getRGB(i,  j);
				if(p>0){
					inner.add(0);
				}else {
					inner.add(1);
				}
			currentPuzzle.add(inner);
			}	
		}
	}
	
	public ArrayList<ArrayList<Integer>> pOutput() {
		return currentPuzzle;
	}
	
	
	
}
