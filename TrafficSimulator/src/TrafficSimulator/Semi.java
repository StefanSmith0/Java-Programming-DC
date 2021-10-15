package TrafficSimulator;

import java.awt.Color;
import java.awt.Graphics;

public class Semi extends Vehicle {
	
	public Semi(int newx, int newy) {
		//Semi stats
		super(newx, newy);
		width = 100;
		height = 40;
		speed = 5;
	}
	
	//Paints itself
	public void paintMe(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}
}
