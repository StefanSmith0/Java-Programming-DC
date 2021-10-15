package com.stefan.midterm;

import java.util.Scanner;

public class Midterm {
	Scanner input = new Scanner(System.in);
	int num;
	boolean negative;
	
	public static void main(String[] args) {
		Midterm instance = new Midterm(); 
		instance.Midterm();
	}

	private void Midterm() {
		
		while (num >= 0) {
			System.out.println("Type a positive number");
			num = input.nextInt();
		}
			System.out.println("You had one job.");
	}
}
