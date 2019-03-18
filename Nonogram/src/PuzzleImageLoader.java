import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class PuzzleImageLoader {
	public BufferedImage img = null;
	List<List<Tile>> currentPuzzle = new ArrayList<List<Tile>>();
	
	PuzzleImageLoader(String address) {
		try {
			img = ImageIO.read(new File(address));
			convertPic();
		}catch(IOException e) {}
	}
	
	public void convertPic() {
		int width = img.getWidth();
		int height = img.getHeight();
		List<Tile> inner = new ArrayList<Tile>();
		Tile x = new Tile(false, 0);
		
		for(int i=0;i<height;i++) {
			for(int j=0; j<width;j++) {
				int p = img.getRGB(i,  j);
				if(p>0){
					x.setFilled(true);
					x.setColor(p);
					inner.add(x);
				}else {
					inner.add(x);
				}
			currentPuzzle.add(inner);
			}	
		}
	}
	
	public List<List<Tile>> pOutput() {
		return currentPuzzle;
	}
	
	
	
}
