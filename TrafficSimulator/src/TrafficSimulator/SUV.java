package TrafficSimulator;

import java.awt.Color;
import java.awt.Graphics;

public class SUV extends Vehicle {

	public SUV(int newx, int newy) {
		//SUV stats
		super(newx, newy);
		width = 60;
		height = 30;
		speed = 8;
	}
	
	//Paints itself
	public void paintMe(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
}
