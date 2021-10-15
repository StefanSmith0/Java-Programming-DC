package com.stefan.gomoku;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gomoku implements ActionListener {

	JFrame frame = new JFrame();
	JButton[][] button = new JButton[5][5];
	int[][] board = new int[5][5];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int FIRST_TURN = 0;
	final int X_TURN = 1;
	final int O_TURN = 2;
	int turn = FIRST_TURN;
	int firstTurnActions = 3;
	Container center = new Container();
	JLabel xLabel = new JLabel("X Wins:0");
	JLabel oLabel = new JLabel("O Wins:0");
	JLabel turnLabel = new JLabel("Swap 2 rule: P1 places two Xs and one O.");
	JPanel turnPanel = new JPanel();
	JButton xChangeName = new JButton("Change X's Name.");
	JButton oChangeName = new JButton("Change O's Name.");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xwins = 0;
	int owins = 0;

	public Gomoku() {
		frame.setSize(375, 400);
		frame.setLayout(new BorderLayout());
		turnPanel.add(turnLabel); // Bottom label.
		frame.add(turnPanel, BorderLayout.SOUTH);
		center.setLayout(new GridLayout(5, 5)); // Center container setup.
		for (int i = 0; i < button.length; i++) { // Create button grid.
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton();
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		north.setLayout(new GridLayout(3, 2)); // North Container.
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "This game uses the Swap 2 rule. \nOn the first turn, "
				+ "P1 gets to place two Xs and one O. \n" + "Then P2 gets to choose to play as either X or O.");
	}

	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[j][i])) { // Button on board?
					gridButton = true;
					current = button[j][i];
					if (board[j][i] == BLANK) { // Space selected is empty?
						if (turn == X_TURN) {
							// Place X, change to O turn.
							current.setText("X");
							board[j][i] = X_MOVE;
							turnLabel.setText("Player " + oPlayerName + "'s turn.");
							turn = O_TURN;
						} else if (turn == O_TURN) {
							// Place O, change to X turn.
							current.setText("O");
							board[j][i] = O_MOVE;
							turnLabel.setText("Player " + xPlayerName + "'s turn.");
							turn = X_TURN;
						} else { // First Turn? (Swap2 turn)
							if (firstTurnActions > 1) { // Place Xs.
								current.setText("X");
								board[j][i] = X_MOVE;
								firstTurnActions--;
							} else if (firstTurnActions == 1) { // Place O, end first turn.
								current.setText("O");
								board[j][i] = O_MOVE;
								firstTurnActions = 3;
								turnLabel.setText("Player " + oPlayerName + "'s turn.");
								JOptionPane.showMessageDialog(frame, "P2 now gets the choice of playing as either X or O.");
								turn = O_TURN;
							}
						}
						current.setEnabled(false);
						// Check for wins and ties.
						if (checkWin(X_MOVE) == true) {
							// X Wins.
							xwins++;
							xLabel.setText(xPlayerName + " wins:" + xwins);
							clearBoard();
						} else if (checkWin(O_MOVE) == true) {
							// O Wins.
							owins++;
							oLabel.setText(oPlayerName + " wins:" + owins);
							clearBoard();
						} else if (checkTie() == true) {
							// Tie.
							clearBoard();
						}
					}
				}
			}
		}
		// Changes player names to ChangeField inputs if field is not empty.
		if (gridButton == false) {
			if (event.getSource().equals(xChangeName) == true && xChangeField.getText().isEmpty() == false) {
				xPlayerName = xChangeField.getText();
				xLabel.setText(xPlayerName + " wins:" + xwins);
			} else if (event.getSource().equals(oChangeName) == true && oChangeField.getText().isEmpty() == false) {
				oPlayerName = oChangeField.getText();
				oLabel.setText(oPlayerName + " wins:" + owins);
			}
		}
	}

	// Checks for either player winning.
	public boolean checkWin(int player) {
		// Check horizontals.
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player && board[0][3] == player
				&& board[0][4] == player) {
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player && board[1][3] == player
				&& board[1][4] == player) {
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player && board[2][3] == player
				&& board[2][4] == player) {
			return true;
		}
		if (board[3][0] == player && board[3][1] == player && board[3][2] == player && board[3][3] == player
				&& board[3][4] == player) {
			return true;
		}
		if (board[4][0] == player && board[4][1] == player && board[4][2] == player && board[4][3] == player
				&& board[4][4] == player) {
			return true;
		}
		// Check Verticals.
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player && board[3][0] == player
				&& board[4][0] == player) {
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player && board[3][1] == player
				&& board[4][1] == player) {
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player && board[3][2] == player
				&& board[4][2] == player) {
			return true;
		}
		if (board[0][3] == player && board[1][3] == player && board[2][3] == player && board[3][3] == player
				&& board[4][3] == player) {
			return true;
		}
		if (board[0][4] == player && board[1][4] == player && board[2][4] == player && board[3][4] == player
				&& board[4][4] == player) {
			return true;
		}
		// Check diagonals.
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player && board[3][3] == player
				&& board[4][4] == player) {
			return true;
		}
		if (board[0][4] == player && board[1][3] == player && board[2][2] == player && board[3][1] == player
				&& board[4][0] == player) {
			return true;
		}
		return false;
	}

	// Checks for ties.
	public boolean checkTie() { // Checks board for empty spaces.
		for (int column = 0; column < board[0].length; column++) {
			for (int row = 0; row < board[0].length; row++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		return true;
	}

	// Clears the board.
	public void clearBoard() { // Makes board all empty spaces.
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board.length; b++) {
				board[a][b] = BLANK;
				button[a][b].setText("");
				button[a][b].setEnabled(true);
			}
		}
		// Reset to first turn.
		turnLabel.setText("Swap 2 rule: P1 places two Xs and one O.");
		turn = FIRST_TURN;
	}

	// Constructor for Gomoku.
	public static void main(String[] args) {
		new Gomoku();
	}
}