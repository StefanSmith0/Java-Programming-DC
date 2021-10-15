package com.stefan.conwayslife;

/*
 * This program is an implementation of Conway's Life. It needs LifePanel.java to work.
 * 10/12/20
 * Stefan Smith
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener, Runnable {

	boolean[][] cells = new boolean[10][10];
	JFrame frame = new JFrame("Life Simulation");
	LifePanel panel = new LifePanel(cells);
	Container south = new Container();
	JButton step = new JButton("Step");
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	boolean running = false;

	// Sets up the frame
	public Life() {
		frame.setSize(600, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);
		// south container
		south.setLayout(new GridLayout(1, 3));
		south.add(step);
		step.addActionListener(this);
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// Constructor for Life
	public static void main(String[] args) {
		new Life();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	// Tracks clicks on a 10x10 grid.
	public void mouseReleased(MouseEvent event) {
		System.out.println(event.getX() + "," + event.getY());
		double width = (double) panel.getWidth() / cells[0].length;
		double height = (double) panel.getHeight() / cells.length;
		int column = Math.min(cells[0].length - 1, (int) (event.getX() / width));
		int row = Math.min(cells.length - 1, (int) (event.getY() / height));
		System.out.println(column + "," + row);
		cells[row][column] = !cells[row][column];
		frame.repaint();
	}

	// Checks for button clicks
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(step)) {
			step();
		}
		if (event.getSource().equals(start)) {
			if (running == false) {
				running = true;
				Thread t = new Thread(this);
				t.start();
			}
		}
		if (event.getSource().equals(stop)) {
			running = false;
		}
	}

	// Runs the simulation after start
	public void run() {
		while (running == true) {
			step();
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// Counts up neighbors, decides to die or not.
	public void step() {
		boolean[][] nextCells = new boolean[cells.length][cells[0].length];
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				int neighborCount = 0;
				if (row > 0 && column > 0 && cells[row - 1][column - 1] == true) {// up left
					neighborCount++;
				}
				if (row > 0 && cells[row - 1][column] == true) {// up
					neighborCount++;
				}
				if (row > 0 && column < cells[0].length - 1 && cells[row - 1][column + 1] == true) {// up right
					neighborCount++;
				}
				if (column > 0 && cells[row][column - 1] == true) {// left
					neighborCount++;
				}
				if (column < cells[0].length - 1 && cells[row][column + 1] == true) {// right
					neighborCount++;
				}
				if (row < cells.length - 1 && column > 0 && cells[row + 1][column - 1] == true) {// down left
					neighborCount++;
				}
				if (row < cells.length - 1 && cells[row + 1][column] == true) {// down
					neighborCount++;
				}
				if (row < cells.length - 1 && column < cells[0].length - 1 && cells[row + 1][column + 1] == true) {// down
																													// right
					neighborCount++;
				}

				// Rules of Life
				if (cells[row][column] == true) { // I live!
					if (neighborCount == 2 || neighborCount == 3) {
						nextCells[row][column] = true; // alive next time
					} else {
						nextCells[row][column] = false; // dead next time
					}
				} else {
					if (neighborCount == 3) {
						nextCells[row][column] = true; // alive next time
					} else {
						nextCells[row][column] = false; // dead next time
					}
				}
			}
		}
		cells = nextCells;
		panel.setCells(nextCells);
		frame.repaint();
	}
}
