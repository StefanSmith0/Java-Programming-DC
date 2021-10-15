package com.stefan.minesweeper;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Minesweeper implements ActionListener {

	JFrame frame = new JFrame();
	JButton[][] button = new JButton[20][20];
	int[][] board = new int[20][20];
	Container center = new Container();
	final int MINE = 9;
	boolean pressed;
	
	//Constructor
	public static void main(String[] args) {
		new Minesweeper();
	}
	
	public Minesweeper() {
		frame.setSize(600,600);
		center.setLayout(new GridLayout(20, 20));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton();
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
		
		layMines();
		layNeighbors();
	}

	//Sets up the buttons next to mines.
	private void layNeighbors() {
		int neighborMines = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != MINE) {
					if (i > 0 && j < board[0].length - 1 && board[i-1][j+1] == MINE) {
						neighborMines++;
					}
					if (j < board[0].length - 1 && board[i][j+1] == MINE) {
						neighborMines++;
					}
					if (i < board.length - 1 && j < board[0].length - 1 && board[i+1][j+1] == MINE) {
						neighborMines++;
					}
					if (i > 0 && board[i-1][j] == MINE) {
						neighborMines++;
					}
					if (i < board.length - 1 && board[i+1][j] == MINE) {
						neighborMines++;
					}
					if (i > 0 && j > 0 && board[i-1][j-1] == MINE) {
						neighborMines++;
					}
					if (j > 0 && board[i][j-1] == MINE) {
						neighborMines++;
					}
					if (j > 0 && i < board.length - 1 && board[i+1][j-1] == MINE) {
						neighborMines++;
					}
					board[i][j] = neighborMines;
					neighborMines = 0;
				}
			}
		}
	}

	//Places mines randomly.
	private void layMines() {
		int max = 20;
		int min = 0;
		int mineNum = 30;
		for (int i = 0; i < mineNum; i++) {
			int randomX = (int) (Math.random() * (max - min));
			int randomY = (int) (Math.random() * (max - min));
			if (board[randomX][randomY] == MINE) {
				mineNum++;
			}
			board[randomX][randomY] = MINE;
		}
	}

	//Reveals button clicked on, checks for losses and wins.
	public void actionPerformed(ActionEvent event) {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[j][i])) {
					if (board[j][i] != MINE) {
						recurseZeroes(j, i);
						button[j][i].setText(Integer.toString(board[j][i]));
						checkWin();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Game Over.");
						gameOver();
					}
				}
			}	
		}
	}

	//Checks for wins.
	private void checkWin() {
		int uncoveredSquares = 0;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (button[j][i].getText() != "" && board[j][i] != MINE) {
					uncoveredSquares++;
				}
			}
		}
		if (uncoveredSquares >= 370) {
			JOptionPane.showMessageDialog(frame, "You Win!");
			gameOver();
		}
	}

	//Ends the game, reveals and disables all buttons.
	private void gameOver() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (board[j][i] != MINE) {
					button[j][i].setText(Integer.toString(board[j][i]));
				}
				else {
					button[j][i].setFont(new Font("Arial", Font.PLAIN, 25));
					button[j][i].setText("✸");
				}
				button[j][i].setEnabled(false);
			}
		}
	}

	//Recursively reveals all neighboring zeroes.
	private void recurseZeroes(int x, int y) {
		if (x >= 0 && y >= 0 && y < board[0].length && x < board.length) {
			if (board[x][y] == 0) {
				if (button[x][y].getText() == "") {
					button[x][y].setText(Integer.toString(board[x][y]));
					recurseZeroes(x - 1, y - 1);
					recurseZeroes(x, y - 1);
					recurseZeroes(x + 1, y - 1);
					recurseZeroes(x - 1, y);
					recurseZeroes(x + 1, y);
					recurseZeroes(x - 1, y + 1);
					recurseZeroes(x, y + 1);
					recurseZeroes(x + 1, y + 1);
				}
			}
		}
	}
}
