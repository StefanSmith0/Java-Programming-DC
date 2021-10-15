package TrafficSimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Road extends JPanel {
	
	final int LANE_HEIGHT = 120;
	final int ROAD_WIDTH = 800;
	ArrayList<Vehicle> cars = new ArrayList<Vehicle>();
	int carCount = 0;
	
	public Road() {
		super();
	}
	
	//adds cars to vehicle list
	public void addCar(Vehicle v) {
		cars.add(v);
	}
	
	//paints the road
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0,  0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		for (int a = LANE_HEIGHT; a < LANE_HEIGHT * 4; a = a + LANE_HEIGHT) { //Which lane
			for (int b = 0; b < getWidth(); b = b + 40) { //Which line
				g.fillRect(b, a, 30, 5);
			}
		}
		//draws cars
		for (int a = 0; a < cars.size(); a++) {
			cars.get(a).paintMe(g);
		}
	}
	
	//manages the movement of each car for each step
	public void step() {
		for (int a = 0; a < cars.size(); a++) {
			Vehicle v = cars.get(a);
			if (collision(v.getX() + v.getSpeed(), v.getY(), v) == false) {//there's no collision
				v.setX(v.getX() + v.getSpeed());
				if (v.getX() > ROAD_WIDTH) {
					if (collision(0, v.getY(), v) == false) {
						v.setX(0);
						carCount++;
					}
				}
			}
			else {// car ahead!
				if ((v.getY() > 40) && 
						(collision(v.getX(), v.getY() - LANE_HEIGHT, v) == false)) {//not at top lane
					v.setY(v.getY() - LANE_HEIGHT);
				} 
				else if ((v.getY() < 40 + 3 * LANE_HEIGHT) && 
						(collision(v.getX(), v.getY() + LANE_HEIGHT, v) == false)) {//not at bottom lane
					v.setY(v.getY() + LANE_HEIGHT);
				}
			}
		}
	}
	
	//detects collisions between vehicles
	public boolean collision(int x, int y, Vehicle v) {
		for (int a = 0; a < cars.size(); a++) {
			Vehicle u = cars.get(a);
			if (y == u.getY()) { //if Im in same lane
				if (u.equals(v) == false) { //if I'm not checking myself
					if (x < u.getX() + u.getWidth() && ////my left side is right of his right side.
							x + v.getWidth() > u.getX()) { //my right side is left of his left side.
						return true; //collision
					}
				}
			}
		}
		return false; //no collision
	}
	
	//gets carCount
	public int getCarCount() {
		return carCount;
	}
	
	//sets carCount to 0
	public void resetCarCount() {
		carCount = 0;
	}
}
