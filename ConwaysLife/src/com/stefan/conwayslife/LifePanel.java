package com.stefan.conwayslife;

/*
 * Panel for Conway's life. Draws grid and cells. It needs Life.java to work
 * 10/12/20
 * Stefan Smith
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LifePanel extends JPanel{
	
	boolean[][] cells;
	double width;
	double height;
	
	public LifePanel(boolean[][] in) {
		cells = in;
	}
	
	public void setCells(boolean[][] newcells) {
		cells = newcells;
	}
	
	//Finds grid dimensions
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = (double)this.getWidth() / cells[0].length;
		height = (double)this.getHeight() / cells.length;
		
		
		//Draws cells
		g.setColor(Color.BLUE);
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if (cells[row][column] == true) {
					g.fillRect((int)Math.round((column*width)), (int)Math.round((row*height)), (int)width+1, (int)height+1);
				}
			}
		}
		
		//Draws grid
		g.setColor(new Color(225,128,0));
		for (int x = 0; x < cells[0].length + 1; x++) {
			g.drawLine((int)Math.round(x*width),0,(int)Math.round(x*width),this.getHeight());
		}
		for (int y = 0; y < cells.length + 1; y++) {
			g.drawLine(0,(int)Math.round(y*height),this.getWidth(),(int)Math.round(y*height));
		}
	}
}
