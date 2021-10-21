/*
 * This game will have two users play tiktactoe against each other in the console.
 * Author: Stefan Smith
 * Date: 10/3/20
 */

package com.stefan.tiktaktoe;

import java.util.Scanner;

public class tiktaktoe {

	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	int X_WINS = 0;
	int O_WINS = 0;

	Scanner scanner;
	String input = "";

	// Main method for running game
	public tiktaktoe() {
		scanner = new Scanner(System.in);
		boolean stillPlaying = true;
		while (stillPlaying == true) {

			while (checkWin(X_MOVE) == false && checkWin(O_MOVE) == false && checkTie() == false) {
				printBoard();
				input = scanner.nextLine();
				if (input.length() != 2) {
					System.out.println("Enter a letter followed by a number");
				} else if (input.charAt(0) != 'a' && input.charAt(0) != 'b' && input.charAt(0) != 'c') {
					System.out.println("Row must be a, b, or c");
				} else if (input.charAt(1) != '1' && input.charAt(1) != '2' && input.charAt(1) != '3') {
					System.out.println("Column must be 1, 2, or 3");
				} else {
					int row = input.charAt(0) - 'a';
					int column = input.charAt(1) - '1';
					if (board[row][column] == BLANK) {
						if (turn == X_TURN) {
							board[row][column] = X_MOVE;
							turn = O_TURN;
						} else {
							board[row][column] = O_MOVE;
							turn = X_TURN;
						}
					} else {
						System.out.println("There is already a piece there!");
					}
				}
			}
			if (checkWin(X_MOVE) == true) {
				System.out.println("----X Wins!----");
				X_WINS++;
			} else if (checkWin(O_MOVE) == true) {
				System.out.println("----O Wins!----");
				O_WINS++;
			}
			System.out.println("X Wins: " + X_WINS + "\nO Wins: " + O_WINS);
			System.out.println("----Play Again?----");
			String yesno = scanner.nextLine();
			if (yesno.equals("yes") || yesno.equals("y")) {
				stillPlaying = true;
				for (int column = 0; column < board[0].length; column++) {
					for (int row = 0; row < board[0].length; row++) {
						board[row][column] = BLANK;
					}
				}
				turn = X_TURN;
			} else {
				stillPlaying = false;
			}
		}
	}

	// Prints the board in console
	public void printBoard() {
		System.out.println(" \t1\t2\t3");
		for (int row = 0; row < board.length; row++) {
			String output = (char) ('a' + row) + " \t";
			for (int column = 0; column < board.length; column++) {
				if (board[row][column] == BLANK) {
					output += " \t";
				} else if (board[row][column] == X_MOVE) {
					output += "X\t";
				} else if (board[row][column] == O_MOVE) {
					output += "O\t";
				}
			}
			System.out.println(output);
		}
	}

	// Checks if either player has won
	public boolean checkWin(int player) {
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
			printBoard();
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
			printBoard();
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
			printBoard();
			return true;
		}
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
			printBoard();
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
			printBoard();
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
			printBoard();
			return true;
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			printBoard();
			return true;
		}
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
			printBoard();
			return true;
		}
		return false;
	}

	// Checks if there is a tie. This check occurs after the win check.
	public boolean checkTie() {
		for (int column = 0; column < board[0].length; column++) {
			for (int row = 0; row < board[0].length; row++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		printBoard();
		System.out.println("Tie!");
		return true;
	}

	// Constructor for tiktaktoe
	public static void main(String[] args) {
		new tiktaktoe();

	}
}
