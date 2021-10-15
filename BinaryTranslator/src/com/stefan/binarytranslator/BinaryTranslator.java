package com.stefan.binarytranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class BinaryTranslator {

	public static void main(String[] args) throws IOException {
		Translator translator = new Translator();
		translator.translate();

	}

}

class Translator {
	public Translator() {}

	public void translate() throws IOException {
		System.out.println("From console or file? (c/f)");
		Scanner scanner=new Scanner(System.in);
		if ("c".equals(scanner.nextLine())) {
			readFromConsole();
		}
		else {
			System.out.println("File Location?");
			Scanner fileLocation=new Scanner(System.in);
			String fileToReader = fileLocation.nextLine ();
			readFromFile(fileToReader);
		}
	}
	public void readFromConsole() {
		System.out.println("Decimal or Binary number to convert:");
		Scanner s=new Scanner(System.in);
		int n=s.nextInt();
		if (isBinary(n)) {
			System.out.println("Binary to Decimal conversion:");
			binaryToDecimal(n);
		}
		else {
			System.out.println("Decimal to Binary conversion:");
			decimalToBinary(n);
		}
	}
	public boolean isBinary(int number) { 
		int copyOfInput = number; 
		while (copyOfInput != 0) { 
			if (copyOfInput % 10 > 1) { 
				return false; 
			} 
			copyOfInput = copyOfInput / 10;
		} 
		return true;
	}

	public void readFromFile(String fileName) throws IOException {
		File file = new File(fileName);


		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st = br.readLine();
		int si = Integer.parseInt(st);
		if (isBinary(si)) {
			System.out.println("Binary to Decimal conversion:");
			binaryToDecimal(si);
		}
		else {
			System.out.println("Decimal to Binary conversion:");
			decimalToBinary(si);
		}
	}

	public void binaryToDecimal(int binary) {        
		int decimal=0,place=0;

		while(binary!=0)
		{
			decimal+=((binary%10)*Math.pow(2,place));
			binary=binary/10;
			place++;
		}

		System.out.println(decimal);
	}
	public void decimalToBinary(int decimal) {
		Stack<Integer> stack = new Stack<Integer>();

		while (decimal != 0)
		{
			int bigit = decimal % 2;
			stack.push(bigit);
			decimal /= 2;
		} 
		while (!(stack.isEmpty() ))
		{
			System.out.print(stack.pop());
		}
		System.out.println();
	}
}

