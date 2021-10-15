import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class board extends JPanel {

	Image image;
	int x;
	int y;
	
	public board() {
		super();
		
		try {
			image = ImageIO.read(new File ("board.jpg"));
			}catch (IOException e){
			e.printStackTrace();
			}
	}
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		//g.drawLine(0, 0, 300, 300);
		g.drawImage(image,x,y,null);
	}

}


