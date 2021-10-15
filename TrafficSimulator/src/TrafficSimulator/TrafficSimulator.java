package TrafficSimulator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TrafficSimulator implements ActionListener, Runnable {

	JFrame frame = new JFrame("Traffic Simulation");
	Road road = new Road();
	//South Container
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	JLabel throughput = new JLabel("Throughput: 0");
	Container south = new Container();
	//West Container
	JButton semi = new JButton("Add Semi");
	JButton suv = new JButton("Add SUV");
	JButton sports = new JButton("Add Sports");
	Container west = new Container();
	int carCount = 0;
	long startTime = 0;
	boolean running = false;
	
	public TrafficSimulator() {
		frame.setSize(1000, 550);
		frame.setLayout(new BorderLayout());
		frame.add(road, BorderLayout.CENTER);
		
		south.setLayout(new GridLayout(1, 3));
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		south.add(throughput);
		frame.add(south, BorderLayout.SOUTH);
		
		west.setLayout(new GridLayout(3, 1));
		west.add(semi);
		semi.addActionListener(this);
		west.add(suv);
		suv.addActionListener(this);
		west.add(sports);
		sports.addActionListener(this);
		frame.add(west, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Constructor
	public static void main(String[] args) {
		new TrafficSimulator();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(start)) {
			if (running == false) {
				running = true;
				road.resetCarCount();
				startTime = System.currentTimeMillis();
				Thread t = new Thread(this);
				t.start();
			}
		}
		
		//manages the stop button, stops the simulation
		if (event.getSource().equals(stop)) {
			running = false;
		}
		
		//manages the semi button
		if (event.getSource().equals(semi)) {
			Semi semi = new Semi(0, 40);
			road.addCar(semi);
			for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
				for (int y = 40; y < 480; y = y + 120) {
					semi.setX(x);
					semi.setY(y);
					if (road.collision(x, y, semi) == false) {
						frame.repaint();
						return;
					}
				}
			}
		}
		//manages the suv button
		if (event.getSource().equals(suv)) {
			SUV suv = new SUV(0, 40);
			road.addCar(suv);
			for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
				for (int y = 40; y < 480; y = y + 120) {
					suv.setX(x);
					suv.setY(y);
					if (road.collision(x, y, suv) == false) {
						frame.repaint();
						return;
					}
				}
			}
		}
		//manages the sports button
		if (event.getSource().equals(sports)) {
			Sports sports = new Sports(0, 40);
			road.addCar(sports);
			for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
				for (int y = 40; y < 480; y = y + 120) {
					sports.setX(x);
					sports.setY(y);
					if (road.collision(x, y, sports) == false) {
						frame.repaint();
						return;
					}
				}
			}
		}
	}

	//begins the simulation
	public void run() {
		while (running == true) {
			road.step();
			carCount = road.getCarCount();
			double throughputCalc = carCount / (1000 * (double)(System.currentTimeMillis() - startTime));
			throughput.setText("Throughput: " + throughputCalc);
			frame.repaint();
			try {
				Thread.sleep(100);
			} catch (Exception x) {
				
			}
		}
	}
}
