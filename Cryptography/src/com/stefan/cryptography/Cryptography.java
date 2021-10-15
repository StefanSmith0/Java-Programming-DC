package com.stefan.cryptography;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cryptography implements ItemListener, ActionListener {

	JFrame frame = new JFrame();
	JTextField decryptedField = new JTextField(20);
	JTextField encryptedField = new JTextField(20);
	JTextField scytaleRows = new JTextField(5);
	JTextField vigenereKeyword = new JTextField(10);
	JTextField caesarShift = new JTextField(5);
	Container center = new Container();
	Container card = new Container();
	Container left = new Container();
	Container right = new Container();
	final static String caesarCombo = "Caesar Cipher";
	final static String vigenereCombo = "Vigenere Cipher";
	final static String scytaleCombo = "Scytale Cipher";
	JButton encrypt = new JButton("Encrypt");
	JButton decrypt = new JButton("Decrypt");
	private enum Cipher {
	    VIGENERE,
	    CAESAR,
	    SCYTALE
	};
	Cipher currentCipher = Cipher.SCYTALE;
	
	
	//Constructor
	public static void main(String[] args) {
		new Cryptography();
	}

	public Cryptography() {
		
		frame.setSize(800,200); frame.setLayout(new BorderLayout());
		center.setLayout(new BorderLayout());
		card.setLayout(new CardLayout());
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		left.add(new JLabel("                    Encrypted Text"), BorderLayout.NORTH);
		left.add(encryptedField, BorderLayout.CENTER);
		right.add(new JLabel("                   Decrypted Text"), BorderLayout.NORTH);
		right.add(decryptedField, BorderLayout.CENTER);
		JPanel comboBoxPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(encrypt, BorderLayout.PAGE_START);
		buttonsPanel.add(decrypt, BorderLayout.PAGE_END);
		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		JPanel scytalePanel = new JPanel();
		scytalePanel.add(new JLabel("Number of rows:"), BorderLayout.PAGE_START);
		scytalePanel.add(scytaleRows, BorderLayout.PAGE_END);
		card.add(scytalePanel, scytaleCombo);
		JPanel vigenerePanel = new JPanel();
		vigenerePanel.add(new JLabel("Keyword:"), BorderLayout.LINE_START);
		vigenerePanel.add(vigenereKeyword, BorderLayout.LINE_END);
		card.add(vigenerePanel, vigenereCombo);
		JPanel caesarPanel = new JPanel();
		caesarPanel.add(new JLabel("Shift:"), BorderLayout.LINE_START);
		caesarPanel.add(caesarShift, BorderLayout.LINE_END);
		card.add(caesarPanel, caesarCombo);
		String comboBoxItems[] = { scytaleCombo, vigenereCombo, caesarCombo };
		JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPanel.add(cb);
		center.add(comboBoxPanel, BorderLayout.PAGE_START);
		center.add(buttonsPanel, BorderLayout.PAGE_END);
		frame.add(left, BorderLayout.EAST);
		frame.add(right, BorderLayout.WEST);
		center.add(card, BorderLayout.CENTER);
		frame.add(center, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);	
	}

	//Capitalizes and removes non-letters from user inputs
	private String cleanUp(String rawinput) {
		String upperRawInput = rawinput.toUpperCase();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < upperRawInput.length(); i++) {
			if (upperRawInput.charAt(i) > 64 && upperRawInput.charAt(i) <= 90) {
				sb.append(upperRawInput.charAt(i));
			}
		}
		return sb.toString();
	}
	
	//Encrypts using scytale
	private String scytale(String input, int numcolumn) {
		int numrow = (int) Math.ceil((input.length() / (float)numcolumn));
		String output = "";
		char[][] inputArray = new char[numrow][numcolumn];
		int rowIndex = 0;
		int columnIndex = 0;
		for (int i = 0; i < input.length(); i++) {
			inputArray[rowIndex][columnIndex] = input.charAt(i);
			if (columnIndex == numcolumn-1) {
				rowIndex++;
				columnIndex = 0;
			}
			else {
				columnIndex++;
			}
		}
		for (int column = 0; column < numcolumn; column++) {
			for (int row = 0; row < numrow; row++) {
				output = output.concat(Character.toString((inputArray[row][column])));
			}
		}
		return output;
	}
	
	//Decrpyts using scytale cipher
	private String reversescytale(String input, int numcolumn) {
		int numrow = (int) Math.ceil((input.length() / (float)numcolumn));
		String output = "";
		char[][] inputArray = new char[numrow][numcolumn];
		int rowIndex = 0;
		int columnIndex = 0;
		for (int i = 0; i < input.length(); i++) {
			inputArray[rowIndex][columnIndex] = input.charAt(i);
			if (rowIndex == numrow-1) {
				columnIndex++;
				rowIndex = 0;
			}
			else {
				rowIndex++;
			}
		}
		for (int row = 0; row < numrow; row++) {
			for (int column = 0; column < numcolumn; column++) {
				output = output.concat(Character.toString((inputArray[row][column])));
			}
		}
		return output; 
	} 
	
	//Encrypts using caesar cipher
	public String caesar(String input, int shift) {
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			int shiftedChar = (input.charAt(i) + shift);
			if (shiftedChar > 90) {
				shiftedChar -= 26;
				output = output.concat(Character.toString(shiftedChar));
			}
			else if (shiftedChar < 65) {
				shiftedChar += 26;
				output = output.concat(Character.toString(shiftedChar));
			}
			else {
				output = output.concat(Character.toString(shiftedChar));
			}
		}
		return output;
	}
	
	//Decrpyts using caesar cipher
	public String reversecaesar(String input, int shift) {
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			int shiftedChar = (input.charAt(i) - shift);
			if (shiftedChar > 90) {
				shiftedChar -= 26;
				output = output.concat(Character.toString(shiftedChar));
			}
			else if (shiftedChar < 65) {
				shiftedChar += 26;
				output = output.concat(Character.toString(shiftedChar));
			}
			else {
				output = output.concat(Character.toString(shiftedChar));
			}
		}
		return output;
	}
	
	//Encrypts using vigenere cipher
	public String vigenere(String input, String keyword) {
		String output = "";
		int shift;
		int keyChar = 0;
		for (int i = 0; i < input.length(); i++) {
			shift = (keyword.charAt(keyChar) - 'A');
			if (keyChar >= keyword.length() - 1) {
				keyChar = 0;
			}
			else {
				keyChar++;
			}
			int shiftedChar = (input.charAt(i) + shift);
			if (shiftedChar > 90) {
				shiftedChar -= 26;
			}
			else if (shiftedChar < 65) {
				shiftedChar += 26;
			}
			output = output.concat(Character.toString(shiftedChar));
		}
		return output;
	}
	
	//Decrpyts using vigenere cipher
	public String reversevigenere(String input, String keyword) {
		String output = "";
		int shift;
		int keyChar = 0;
		for (int i = 0; i < input.length(); i++) {
			shift = (keyword.charAt(keyChar) - 'A');
			if (keyChar >= keyword.length() - 1) {
				keyChar = 0;
			}
			else {
				keyChar++;
			}
			int shiftedChar = (input.charAt(i) - shift);
			if (shiftedChar > 90) {
				shiftedChar -= 26;
			}
			else if (shiftedChar < 65) {
				shiftedChar += 26;
			}
			output = output.concat(Character.toString(shiftedChar));
		}
		return output;
	}

	//Runs comboBox, keeps track of comboBox state
	public void itemStateChanged(ItemEvent event) {
	    CardLayout cl = (CardLayout)(card.getLayout());
	    cl.show(card, (String)event.getItem());
	    if (event.getItem().equals(caesarCombo)) {
	    	currentCipher = Cipher.CAESAR;
	    }
	    else if (event.getItem().equals(vigenereCombo)) {
	    	currentCipher = Cipher.VIGENERE;
	    }
	    else {
	    	currentCipher = Cipher.SCYTALE;
	    }
	}

	//Runs the encrypt and decrypt buttons based on comboBox state
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("Encrypt")) {	
			String input = cleanUp(decryptedField.getText());
			if (currentCipher == Cipher.SCYTALE) {
				for (int i = 0; i < scytaleRows.getText().length(); i++) {
					if (scytaleRows.getText().charAt(i) <= 57 && scytaleRows.getText().charAt(i) >= 48) {
						int numcolumns = Integer.parseInt(scytaleRows.getText());
						String output = scytale(input, numcolumns);
						encryptedField.setText(output);
					}
					else {
						scytaleRows.setText("");
						JOptionPane.showMessageDialog(frame, "The number of rows must be a number.");

					}
				}
			}
			else if (currentCipher == Cipher.VIGENERE) {
				String keyword = cleanUp(vigenereKeyword.getText());
				if (keyword.length() < 1) {
					vigenereKeyword.setText("");
					JOptionPane.showMessageDialog(frame, "The keyword must contain at least one letter.");
				}
				else {
					String output = vigenere(input, keyword);
					encryptedField.setText(output);
				}
			}
			else if (currentCipher == Cipher.CAESAR) {
				for (int i = 0; i < caesarShift.getText().length(); i++) {
					if (caesarShift.getText().charAt(i) <= 57 && caesarShift.getText().charAt(i) >= 48) {
						int shift = Integer.parseInt(caesarShift.getText());
						String output = caesar(input, shift);
						encryptedField.setText(output);
					}
					else {
						caesarShift.setText("");
						JOptionPane.showMessageDialog(frame, "The shift must be a number.");

					}
				}
			}
		}
		if (event.getActionCommand().equals("Decrypt")) {	
			String input = cleanUp(encryptedField.getText());
			if (currentCipher == Cipher.SCYTALE) {
				for (int i = 0; i < scytaleRows.getText().length(); i++) {
					if (scytaleRows.getText().charAt(i) <= 57 && scytaleRows.getText().charAt(i) >= 48) {
						int numcolumns = Integer.parseInt(scytaleRows.getText());
						String output = reversescytale(input, numcolumns);
						decryptedField.setText(output);
					}
					else {
						scytaleRows.setText("");
						JOptionPane.showMessageDialog(frame, "The number of columns must be a number.");

					}
				}
			}
			else if (currentCipher == Cipher.VIGENERE) {
				String keyword = cleanUp(vigenereKeyword.getText());
				if (keyword.length() < 1) {
					vigenereKeyword.setText("");
					JOptionPane.showMessageDialog(frame, "The keyword must contain at least one letter.");
				}
				else {
					String output = reversevigenere(input, keyword);
					decryptedField.setText(output);
				}
			}
			else if (currentCipher == Cipher.CAESAR) {
				for (int i = 0; i < caesarShift.getText().length(); i++) {
					if (caesarShift.getText().charAt(i) <= 57 && caesarShift.getText().charAt(i) >= 48) {
						int shift = Integer.parseInt(caesarShift.getText());
						String output = reversecaesar(input, shift);
						decryptedField.setText(output);
					}
					else {
						caesarShift.setText("");
						JOptionPane.showMessageDialog(frame, "The shift must be a number.");

					}
				}
			}
		}
	}
}
