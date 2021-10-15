package TrafficSimulator;

import java.awt.Color;
import java.awt.Graphics;

public class Sports extends Vehicle{

	public Sports(int newx, int newy) {
		//Sports stats
		super(newx, newy);
		width = 40;
		height = 20;
		speed = 12;
	}
	
	//Paints itself
	public void paintMe(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
