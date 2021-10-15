package com.stefan.guessinggame;

import java.util.Scanner;

public class GuessGame {
	
	public GuessGame () {
		boolean stillPlaying = true;
		
		while (stillPlaying == true) {
			System.out.println("I'm thinking of a number between 0 and 50. (It could be 0 or 50)");
			int guessesTaken = 0;
			int guessesLeft = 10;
			System.out.println("If you guess it in " + guessesLeft + " tries, you will be rewarded with a free 2020 Toyota Prius!");
			int randomNum = (int)(Math.random() * 51);
			//System.out.println(randomNum);
			Scanner scanner = new Scanner(System.in);
			int guess = -1;
			while (guess != randomNum && guessesLeft > 0) {
				System.out.println("You have " + guessesLeft + " guesses left.");
				guessesTaken++;
				guessesLeft--;
				String input = scanner.nextLine();
				System.out.println("You Typed:" + input);
				guess = Integer.parseInt(input);
				if (guess < randomNum) {
					System.out.println("Too low");
				}
				if (guess > randomNum) {
					System.out.println("Too high");
				}
			}
			if (guess == randomNum) {
				System.out.println("Congradulations! You Won in " + guessesTaken + " guesses.");
				System.out.println("Collect your 2020 Toyota Prius now!");
				System.out.println("Credit Card Number: ");
			}
			System.out.println("Play Again?");
			String yesno = scanner.nextLine();
			if (yesno.equals("yes") || yesno.equals("y")) {
				stillPlaying = true;
			}
			else {
				stillPlaying = false;
			}
		}
	}
	
	public static void main (String[] args) {
		new GuessGame();
	}
	
}