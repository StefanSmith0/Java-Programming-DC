package guiTicTacToe;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GuiTicTacToe implements ActionListener {

	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	Container center = new Container();
	JLabel xLabel = new JLabel("X Wins:0");
	JLabel oLabel = new JLabel("O Wins:0");
	JButton xChangeName = new JButton("Change X's Name.");
	JButton oChangeName = new JButton("Change O's Name.");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xwins = 0;
	int owins = 0;

	public GuiTicTacToe() {
		frame.setSize(400, 400);

		// Center grid container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton();
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		// North Container
		north.setLayout(new GridLayout(3, 2));
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
	}

	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[j][i])) {
					gridButton = true;
					current = button[j][i];
					if (board[j][i] == BLANK) {
						if (turn == X_TURN) {
							current.setText("X");
							current.setEnabled(false);
							board[j][i] = X_MOVE;
							turn = O_TURN;
						} else {
							current.setText("O");
							current.setEnabled(false);
							board[j][i] = O_MOVE;
							turn = X_TURN;
						}
						// Check for wins and ties
						if (checkWin(X_MOVE) == true) {
							// X Wins
							xwins++;
							xLabel.setText(xPlayerName + " wins:" + xwins);
							clearBoard();
						} else if (checkWin(O_MOVE) == true) {
							// O Wins
							owins++;
							oLabel.setText(oPlayerName + " wins:" + owins);
							clearBoard();
						} else if (checkTie() == true) {
							// Tie
							clearBoard();
						}
					}
				}
			}
		}
		// Changes player names to ChangeField inputs if input is not empty.
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

	// Checks for either player winning
	public boolean checkWin(int player) {
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
			return true;
		}
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
			;
			return true;
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			return true;
		}
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
			return true;
		}
		return false;
	}

	// Checks for ties
	public boolean checkTie() {
		for (int column = 0; column < board[0].length; column++) {
			for (int row = 0; row < board[0].length; row++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		return true;
	}

	// Clears the board
	public void clearBoard() {
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board.length; b++) {
				board[a][b] = BLANK;
				button[a][b].setText("");
				button[a][b].setEnabled(true);
			}
		}
		turn = X_TURN;
	}

	// Constructor for guiTicTacToe
	public static void main(String[] args) {
		new GuiTicTacToe();
	}
}